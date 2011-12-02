package ZeroFrame.Extensions.Toolbox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ZeroFrame.Extensions.Module;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataTypes;

/**
 * A factory for working with datasets, records, and other data objects
 * 
 * @author Hammer
 */
public class DataFactory {
	// ********************************************************************************************
	// Properties
	Module boundModule = null;

	// ********************************************************************************************
	// constructor
	public DataFactory(Module bindingModule) {
		boundModule = bindingModule;
	}

	// ********************************************************************************************
	// Classes and Enums
	public enum DataTypes {
		INTEGER, LONG, FLOAT, DOUBLE, BOOLEAN, STRING, DATA_SET
	}

	public abstract class DataType {
		
		public abstract DataFactory.DataTypes getType();
		
		protected boolean set = false;
		
		public boolean isset(){
			return set;
		}
	}

	public class DataSet {

		private String name = null;
		private String prefix = null;
		private HashMap<String, DataTypes> fields = new HashMap<String, DataTypes>();
		private HashMap<String, DataType> emptySet = new HashMap<String, DataType>();
		private ArrayList<DataRecord> records = new ArrayList<DataRecord>();
		private int recordPointer = 0;
		private String tableName = null;

		public DataSet(String datasetName) {
			name = datasetName;
			prefix = boundModule.getName();
			tableName = prefix + name;
		}

		public void fieldAdd(String fieldName, DataTypes fieldType) {
			if (!fields.containsKey(fieldName)) {
				fields.put(fieldName, fieldType);
				switch (fieldType) {
					case INTEGER:
						emptySet.put(fieldName, new IntegerDataType());
						break;
					case LONG:
						emptySet.put(fieldName, new LongDataType());
						break;
					case FLOAT:
						emptySet.put(fieldName, new FloatDataType());
						break;
					case DOUBLE:
						emptySet.put(fieldName, new DoubleDataType());
						break;
					case BOOLEAN:
						emptySet.put(fieldName, new BooleanDataType());
						break;
					case STRING:
						emptySet.put(fieldName, new StringDataType());
						break;
					case DATA_SET:
						emptySet.put(fieldName, new DataSetDataType());
						break;
					default:
						break;
				}
			}
		}

		public void fieldAlter(String fieldName, DataTypes fieldType) throws FieldNotFoundException {
			if (fields.containsKey(fieldName)) {
				fields.put(fieldName, fieldType);
				switch (fieldType) {
					case INTEGER:
						emptySet.put(fieldName, new IntegerDataType());
						break;
					case LONG:
						emptySet.put(fieldName, new LongDataType());
						break;
					case FLOAT:
						emptySet.put(fieldName, new FloatDataType());
						break;
					case DOUBLE:
						emptySet.put(fieldName, new DoubleDataType());
						break;
					case BOOLEAN:
						emptySet.put(fieldName, new BooleanDataType());
						break;
					case STRING:
						emptySet.put(fieldName, new StringDataType());
						break;
					case DATA_SET:
						emptySet.put(fieldName, new DataSetDataType());
						break;
					default:
						break;
				}
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public DataRecord getRecordTemplate(){
			return new DataRecord(-1, emptySet, this);
		}

		public void clearCached() {
			records.clear();
		}

		public void clearStored() {
			truncateDB();
		}

		public void clearAll() {
			records.clear();
			truncateDB();
		}

		public void erase() {
			records.clear();
			truncateDB();
			eraseDB();
		}

		public void loadAll() {
			String sql = "SELECT * FROM " + tableName;
			runSQLLoad(sql);
		}

		public void loadFiltered(String fieldName, Object filterObject) {
			
		}

		public void filter(String fieldName, Object filterObject) {
			
		}

		public void insertRecord(DataRecord record) {

		}
		
		public void updateRecord(DataRecord record){
			runSQLUpdate(record);
		}

		public void deleteRecord(DataRecord record) {
			deleteRecordFromDataBase((record.getRecordID()));
			records.remove(record);
		}

		public DataRecord getCurrentRecord() {
			return records.get(recordPointer);
		}

		public DataRecord next() {
			
		}


		private boolean checkCreateTable() {
			String sql = "SELECT " + tableName + " FROM SYSTABLES";
			ResultSet result = ZeroFrame.Data.DatabaseController.executeQuery(sql);
			int i = 0;
			try {
				while (result.next()){
					i++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(i == 0){
				return false;
			}else{
				return true;
			}
		}

		private void truncateDB() {
			// TODO: truncate bound tables
		}

		private void eraseDB() {
			// TODO: erase bound tables
		}

		private void deleteRecordFromDataBase(int recordID) {
			String sql = "DELETE FROM " + tableName + " WHERE RECORDID = " + Integer.toString(recordID);
			runSQLCommand(sql);
		}

		private void runSQLCommand(String sqlStatement) {
			ZeroFrame.Data.DatabaseController.executeGeneric(sqlStatement);
		}

		private void runSQLUpdate(DataRecord record) {

		}

		private void runSQLLoad(String sqlStatement) {
			ResultSet result = ZeroFrame.Data.DatabaseController.executeQuery(sqlStatement);
			records.clear();			
			try {
				while (result.next()) {
					DataRecord record = new DataRecord(Integer.parseInt(result.getString("SYSRECORDID")), emptySet, this);
					Iterator<Entry<String, DataTypes>> iterator = fields.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<String, DataTypes> entry = (Map.Entry<String, DataTypes>) iterator.next();
						switch (entry.getValue()) {
							case INTEGER:
								record.setValue(entry.getKey(), new IntegerDataType(Integer.parseInt(result.getString(entry.getKey()))));
								break;
							case LONG:
								record.setValue(entry.getKey(), new LongDataType(Long.parseLong(result.getString(entry.getKey()))));
								break;
							case FLOAT:
								record.setValue(entry.getKey(), new FloatDataType(Float.parseFloat(result.getString(entry.getKey()))));
								break;
							case DOUBLE:
								record.setValue(entry.getKey(), new DoubleDataType(Double.parseDouble(result.getString(entry.getKey()))));
								break;
							case BOOLEAN:
								record.setValue(entry.getKey(), new BooleanDataType(Boolean.parseBoolean(result.getString(entry.getKey()))));
								break;
							case STRING:
								record.setValue(entry.getKey(), new StringDataType(result.getString(entry.getKey())));
								break;
							case DATA_SET:
								//TODO: Add functionality for datasets existing as fields in records							
								break;
							default:
								break;
						}
					}
					records.add(record);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * A dataset must be initialized in order store data and retrieve data
		 * stored previously.
		 */
		public void initialize() {

		}

	}

	public class DataRecord {

		private HashMap<String, DataType> fields = new HashMap<String, DataType>();
		private int recordid = -1;
		private DataSet parentWatcher = null;

		protected DataRecord(int recordID, HashMap<String, DataType> emptySet, DataSet parentDataSet) {
			recordid = recordID;
			fields = emptySet;
			parentWatcher = parentDataSet;
		}

		public int getRecordID() {
			return recordid;
		}

		public HashMap<String, DataType> getFieldMap() {
			return fields;
		}

		public DataType getFieldValue(String fieldName)
				throws FieldNotFoundException {
			if (fields.containsKey(fieldName)) {
				return fields.get(fieldName);
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}

		public void setValue(String fieldName, DataType value) {
			if (fields.containsKey(fieldName)) {
				//TODO: check for field compatibility
				fields.put(fieldName, value);
				if(parentWatcher != null){
					parentWatcher.updateRecord(this);
				}
			}
		}

		public DataTypes getFieldType(String fieldName)
				throws FieldNotFoundException {
			if (fields.containsKey(fieldName)) {
				return fields.get(fieldName).getType();
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		protected void setParentDataSet(DataSet parentDataSet){
			parentWatcher = parentDataSet;
		}
	}
	
	// ********************************************************************************************
	//Data Types
	
	public class IntegerDataType extends DataType{
		
		private int value;
		
		@Override
		public DataTypes getType() {
			return DataTypes.INTEGER;
		}
		
		public IntegerDataType(){}
		
		public IntegerDataType(int intValue){
			value = intValue;
			set = true;
		}
		
		public void setValue(int intValue){
			value = intValue;
			set = true;
		}
		
		public int getValue(){
			return value;
		}
		
	}
	
	public class LongDataType extends DataType{
		
		private Long value;
		
		@Override
		public DataTypes getType() {
			return DataTypes.LONG;
		}		
		
		public LongDataType(){}
		
		public LongDataType(Long longValue){
			value = longValue;
			set = true;
		}
		
		public Long getValue(){
			return value;
		}
		
		public void setValue(Long longValue){
			value = longValue;
			set = true;
		}
	
	}
	
	public class FloatDataType extends DataType{
		
		private float value;
		
		@Override
		public DataTypes getType() {
			return DataTypes.FLOAT;
		}
		
		public FloatDataType(){}
		
		public FloatDataType(Float floatValue){
			value = floatValue;
			set = true;
		}
		
		public void setValue(Float floatValue){
			value = floatValue;
			set = true;
		}
		
		public float getValue(){
			return value;
		}
		
	}
	
	public class DoubleDataType extends DataType{
		
		private double value;
		
		@Override
		public DataTypes getType() {
			return DataTypes.DOUBLE;
		}
		
		public DoubleDataType(){}
		
		public DoubleDataType(double doubleValue){
			value = doubleValue;
			set = true;
		}
		
		public void setValue(double doubleValue){
			value = doubleValue;
			set = true;
		}
		
		public double getValue(){
			return value;
		}
		
	}
	
	public class BooleanDataType extends DataType{
		
		private boolean value;
		
		@Override
		public DataTypes getType() {
			return DataTypes.BOOLEAN;
		}		
		
		public BooleanDataType(){}
		
		public BooleanDataType(boolean boolValue){
			value = boolValue;
			set = true;
		}
		
		public void setValue(boolean boolValue){
			value = boolValue;
			set = true;
		}
		
		public boolean getValue(){
			return value;
		}
		
	}
	
	public class StringDataType extends DataType{
		
		private String value;
		
		@Override
		public DataTypes getType() {
			return DataTypes.STRING;
		}		
		
		public StringDataType(){}
		
		public StringDataType(String stringValue){
			value = stringValue;
			set = true;
		}
		
		public void setValue(String stringValue){
			value = stringValue;
			set = true;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	public class DataSetDataType extends DataType{
		
		private DataSet value;
		
		@Override
		public DataTypes getType() {
			return DataTypes.DATA_SET;
		}
		
		public DataSetDataType(){}
		
		public DataSetDataType(DataSet dataSet){
			value = dataSet;
			set = true;
		}
		
		public void setDataSet(DataSet dataSet){
			value = dataSet;
			set = true;
		}
		
		public DataSet getDataSet(){
			return value;
		}
		
	}
	
	

	// ********************************************************************************************
	// Exceptions

	public class IncompatibleDataTypeException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IncompatibleDataTypeException(DataTypes expected,
				DataTypes recieved) {
			super("The expected DataType: " + expected.toString()
					+ " Does not match the recieved DataType: "
					+ recieved.toString());
		}
	}

	public class CompatibleFieldNotFoundException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;

		public CompatibleFieldNotFoundException(DataTypes expected,
				String fieldName) {
			super("No field of DataType: " + expected.toString()
					+ " with name: " + fieldName + " was found!");
		}
	}

	public class FieldNotFoundException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3L;

		public FieldNotFoundException(String fieldName) {
			super("No field of name: " + fieldName + " was found!");
		}
	}
	
	public class DataTypeValueNotSetException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4L;
		
		public DataTypeValueNotSetException(){
			super("No value was set for this Data Type Object before attempting storage!");
		}
	}
}
