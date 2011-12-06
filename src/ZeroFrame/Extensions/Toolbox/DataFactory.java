package ZeroFrame.Extensions.Toolbox;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import ZeroFrame.Extensions.Module;

/**
 * A factory for working with data sets, records, and other data objects
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
		protected DataRecord parent;
		protected boolean set = false;
		
		public boolean isset(){
			return set;
		}
		
		protected void update(){
			parent.update();
		}
	}

	public class DataSet {

		private String name = null;
		private String prefix = null;
		private HashMap<String, DataTypes> fields = new HashMap<String, DataTypes>();
		private HashMap<String, DataType> emptySet = new HashMap<String, DataType>();
		private DataRecord defaultRecord;
		private ArrayList<DataRecord> records = new ArrayList<DataRecord>();
		private int recordPointer = 0;
		private String tableName = null;
		private boolean initialized = false;

		public DataSet(String datasetName) {
			name = cleanStringForDatabase(datasetName).toUpperCase();
			prefix = cleanStringForDatabase(boundModule.getName()).toUpperCase();
			tableName = prefix + "_" + name;
		}

		public void fieldAdd(String fieldName, DataTypes fieldType) throws DataSetLockedException {
			if(initialized){throw new DataSetLockedException();}
			fieldName = cleanStringForDatabase(fieldName);
			if (!fields.containsKey(fieldName)) {
				fields.put(fieldName, fieldType);
				switch (fieldType) {
					case INTEGER:
						emptySet.put(fieldName, new IntegerDataType(defaultRecord));
						break;
					case LONG:
						emptySet.put(fieldName, new LongDataType(defaultRecord));
						break;
					case FLOAT:
						emptySet.put(fieldName, new FloatDataType(defaultRecord));
						break;
					case DOUBLE:
						emptySet.put(fieldName, new DoubleDataType(defaultRecord));
						break;
					case BOOLEAN:
						emptySet.put(fieldName, new BooleanDataType(defaultRecord));
						break;
					case STRING:
						emptySet.put(fieldName, new StringDataType(defaultRecord));
						break;
					case DATA_SET:
						emptySet.put(fieldName, new DataSetDataType(defaultRecord));
						break;
					default:
						break;
				}
			}
		}

		public void fieldAlter(String fieldName, DataTypes fieldType) throws FieldNotFoundException, DataSetLockedException {
			if(initialized){throw new DataSetLockedException();}
			fieldName = cleanStringForDatabase(fieldName);
			if (fields.containsKey(fieldName)) {
				fields.put(fieldName, fieldType);
				switch (fieldType) {
					case INTEGER:
						emptySet.put(fieldName, new IntegerDataType(defaultRecord));
						break;
					case LONG:
						emptySet.put(fieldName, new LongDataType(defaultRecord));
						break;
					case FLOAT:
						emptySet.put(fieldName, new FloatDataType(defaultRecord));
						break;
					case DOUBLE:
						emptySet.put(fieldName, new DoubleDataType(defaultRecord));
						break;
					case BOOLEAN:
						emptySet.put(fieldName, new BooleanDataType(defaultRecord));
						break;
					case STRING:
						emptySet.put(fieldName, new StringDataType(defaultRecord));
						break;
					case DATA_SET:
						emptySet.put(fieldName, new DataSetDataType(defaultRecord));
						break;
					default:
						break;
				}
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public DataRecord getRecordTemplate() throws DataSetNotInitializedException{
			if(!initialized){throw new DataSetNotInitializedException();}
			return new DataRecord(-1, emptySet, this);
		}

		public void clearCached() throws DataSetNotInitializedException {
			if(!initialized){throw new DataSetNotInitializedException();}
			records.clear();
			recordPointer = 0;
		}

		public void clearStored() throws DataSetNotInitializedException {
			if(!initialized){throw new DataSetNotInitializedException();}
			truncateDB();
			recordPointer = 0;
		}

		public void clearAll() throws DataSetNotInitializedException {
			if(!initialized){throw new DataSetNotInitializedException();}
			records.clear();
			truncateDB();
			recordPointer = 0;
		}

		public void erase() throws DataSetNotInitializedException {
			if(!initialized){throw new DataSetNotInitializedException();}
			records.clear();
			truncateDB();
			eraseDB();
			recordPointer = 0;
		}

		public void loadAll() throws DataSetNotInitializedException {
			if(!initialized){throw new DataSetNotInitializedException();}
			String sql = "SELECT * FROM " + tableName;
			try {
				//this is a simple query that is sure to work.
				runSQLLoad(sql);
			} catch (FieldNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IncompatibleDataTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public void loadFiltered(String fieldName, DataType filterObject) throws FieldNotFoundException, CompatibleFieldNotFoundException, DataSetNotInitializedException, IncompatibleDataTypeException, FilterValueNotSetException {
			if(!initialized){throw new DataSetNotInitializedException();}
			fieldName = cleanStringForDatabase(fieldName);
			//make sure field exists
			if(!fields.containsKey(fieldName)){
				throw new FieldNotFoundException(fieldName);
			}
			//get field type of named field
			DataTypes recordType = fields.get(fieldName);
			//make sure types match
			if(recordType != filterObject.getType()){
				throw new CompatibleFieldNotFoundException(filterObject.getType(), fieldName);
			}
			//make sure the filter object has a value
			if(!filterObject.set){
				throw new FilterValueNotSetException();
			}
			String sql = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = ";
			switch (filterObject.getType()) {
				case INTEGER:
					IntegerDataType intfilter = (IntegerDataType) filterObject;
					sql += Integer.toString(intfilter.getValue());
					break;
				case LONG:
					LongDataType longfilter = (LongDataType) filterObject;
					sql += Long.toString(longfilter.getValue());
					break;
				case FLOAT:
					FloatDataType floatfilter = (FloatDataType) filterObject;
					sql += Float.toString(floatfilter.getValue());
					break;
				case DOUBLE:
					DoubleDataType doublefilter = (DoubleDataType) filterObject;
					sql += Double.toString(doublefilter.getValue());
					break;
				case BOOLEAN:
					BooleanDataType booleanfilter = (BooleanDataType) filterObject;
					sql += Boolean.toString(booleanfilter.getValue());
					break;
				case STRING:
					StringDataType stringfilter = (StringDataType) filterObject;
					sql += "'" + stringfilter.getValue() + "'";
					break;
				case DATA_SET:
					//The following wont work as a data set is a reference type, will need to update this
					//TODO: filter by dataset
					/*
					DataSetDataType datasetfilter = (DataSetDataType) filterObject;
					*/
					break;
				default:
					break;
			}
			runSQLLoad(sql);
		}

		public void filter(String fieldName, DataType filterObject) throws FieldNotFoundException, CompatibleFieldNotFoundException, DataSetNotInitializedException, FilterValueNotSetException {
			if(!initialized){throw new DataSetNotInitializedException();}
			//make sure field exists
			fieldName = cleanStringForDatabase(fieldName);
			if(!fields.containsKey(fieldName)){
				throw new FieldNotFoundException(fieldName);
			}
			//get field type of named field
			DataTypes recordType = fields.get(fieldName);
			//make sure types match
			if(recordType != filterObject.getType()){
				throw new CompatibleFieldNotFoundException(filterObject.getType(), fieldName);
			}			
			//make sure the filter object has a value
			if(!filterObject.set){
				throw new FilterValueNotSetException();
			}
			//loop through records and remove any record that matches the filterObjects value for
			// the specified field
			for(DataRecord record: records){
				//get the fields DataType object
				DataType fieldValue = record.getFieldValue(fieldName);
				//switch on the type of field and compare values, remove record if values match
				switch (fieldValue.getType()) {
				case INTEGER:
					IntegerDataType intfield = (IntegerDataType) fieldValue;
					IntegerDataType intfilter = (IntegerDataType) filterObject;
					if(intfield.getValue() != intfilter.getValue()){
						records.remove(record);
					}
					break;
				case LONG:
					LongDataType longfield = (LongDataType) fieldValue;
					LongDataType longfilter = (LongDataType) filterObject;
					if(longfield.getValue() != longfilter.getValue()){
						records.remove(record);
					}
					break;
				case FLOAT:
					FloatDataType floatfield = (FloatDataType) fieldValue;
					FloatDataType floatfilter = (FloatDataType) filterObject;
					if(floatfield.getValue() != floatfilter.getValue()){
						records.remove(record);
					}
					break;
				case DOUBLE:
					DoubleDataType doublefield = (DoubleDataType) fieldValue;
					DoubleDataType doublefilter = (DoubleDataType) filterObject;
					if(doublefield.getValue() != doublefilter.getValue()){
						records.remove(record);
					}
					break;
				case BOOLEAN:
					BooleanDataType booleanfield = (BooleanDataType) fieldValue;
					BooleanDataType booleanfilter = (BooleanDataType) filterObject;
					if(booleanfield.getValue() != booleanfilter.getValue()){
						records.remove(record);
					}
					break;
				case STRING:
					StringDataType stringfield = (StringDataType) fieldValue;
					StringDataType stringfilter = (StringDataType) filterObject;
					if(stringfield.getValue() != stringfilter.getValue()){
						records.remove(record);
					}
					break;
				case DATA_SET:
					//The following wont work as a data set is a reference type, will need to update this
					//TODO: filter by dataset
					/*
					DataSetDataType datasetfield = (DataSetDataType) fieldValue;
					DataSetDataType datasetfilter = (DataSetDataType) filterObject;
					if(datasetfield.getValue() != datasetfilter.getValue()){
						records.remove(record);
					}
					*/
					break;
				default:
					break;
				}
			}
			recordPointer = 0;
		}

		public void insertRecord(DataRecord record) throws DataSetNotInitializedException {
			if(!initialized){throw new DataSetNotInitializedException();}
			if(checkIfRecordExists(record)){
				updateRecord(record);
			}else{
				String sql = "INSERT INTO " + tableName + " (";
				//Loop through the fileds and add to the sql string
				Iterator<Entry<String, DataTypes>> iterator = fields.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, DataTypes> entry = (Map.Entry<String, DataTypes>) iterator.next();
					String fieldName = entry.getKey();
					sql += fieldName + ", ";
				}
				int lastIdx = sql.length() - 2;
			    sql = sql.substring(0, lastIdx);
				sql += ") VALUES (";
				iterator = fields.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, DataTypes> entry = (Map.Entry<String, DataTypes>) iterator.next();
					try{ //Got to put it in a try for the FieldNotFoundException
						switch (entry.getValue()) {
							case INTEGER:
								IntegerDataType tempint = ((IntegerDataType)record.getFieldValue(entry.getKey()));
								if(tempint.set){
									sql += tempint.getValue() + ", ";
								}else{
									sql += "NULL, ";
								}
								break;
							case LONG:
								LongDataType templong = ((LongDataType)record.getFieldValue(entry.getKey()));
								if(templong.set){
									sql += templong.getValue() + ", ";
								}else{
									sql += "NULL, ";
								}
								break;
							case FLOAT:
								FloatDataType tempfloat = ((FloatDataType)record.getFieldValue(entry.getKey()));
								if(tempfloat.set){
									sql += tempfloat.getValue() + ", ";
								}else{
									sql += "NULL, ";
								}
								break;
							case DOUBLE:
								DoubleDataType tempdouble = ((DoubleDataType)record.getFieldValue(entry.getKey()));
								if(tempdouble.set){
									sql += tempdouble.getValue() + ", ";
								}else{
									sql += "NULL, ";
								}
								break;
							case BOOLEAN:
								String textRepresentation = null;
								BooleanDataType tempboolean = ((BooleanDataType)record.getFieldValue(entry.getKey()));
								if(tempboolean.set){
									if(((BooleanDataType)record.getFieldValue(entry.getKey())).getValue()){
										textRepresentation = "true";
									}else{
										textRepresentation = "false";
									}
									sql += "'" + textRepresentation + "', ";
								}else{
									sql += "NULL, ";
								}								
								break;
							case STRING:
								StringDataType tempstring = ((StringDataType)record.getFieldValue(entry.getKey()));
								if(tempstring.set){
									sql += "'" + tempstring.getValue() + "', ";
								}else{
									sql += "NULL, ";
								}
								break;
							case DATA_SET:
								//TODO: Add functionality for datasets existing as fields in records							
								break;
							default:
								break;
						}
					}catch(FieldNotFoundException exception){
						//would never actually happen
					}
				}
				lastIdx = sql.length() - 2;
			    sql = sql.substring(0, lastIdx);
				sql += ")";				
				int newId = ZeroFrame.Data.DatabaseController.executeInsert(sql);
				record.recordid = newId;
			}
		}
		
		public void updateRecord(DataRecord record) throws DataSetNotInitializedException{
			if(!initialized){throw new DataSetNotInitializedException();}
			runSQLUpdate(record);
		}

		public void deleteRecord(DataRecord record) throws DataSetNotInitializedException {
			if(!initialized){throw new DataSetNotInitializedException();}
			deleteRecordFromDataBase((record.getRecordID()));
			records.remove(record);
		}
		
		private boolean checkIfRecordExists(DataRecord record){
			//TODO: Not entirely sure... but if someone was to delete a record but still have the record
			//object, but then went to update that record... I think it would break everything... I will 
			//need to look into this when I have more time.
			if(record.getRecordID() != -1){
				return true;
			}
			for(DataRecord loopRecord: records){
				if(loopRecord.getRecordID() == record.getRecordID()){
					return true;
				}
			}
			return false;
		}

		public DataRecord getCurrentRecord() {
			return records.get(recordPointer);
		}

		public DataRecord next() {
			if(records.size() > recordPointer){
				DataRecord returnable = records.get(recordPointer);
				recordPointer++;
				return returnable;
			}else{
				recordPointer = 0;
				return null;
			}
		}

		private boolean checkTableExists() {
			DatabaseMetaData mData = ZeroFrame.Data.DatabaseController.getMetaData();
			String[] tableTypes = {"TABLE"};
			ResultSet result = null;
			try {
				result = mData.getTables(null, null, null, tableTypes);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				while (result.next()){
					if(result.getString("TABLE_NAME").equals(tableName)){
						return true;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		private void truncateDB() {
			String sql = "DELETE FROM " + tableName;
			runSQLCommand(sql);
		}

		private void eraseDB() {
			// TODO: if I am to implement the ability to have datasets within datasets, there will need
			//to be more than a primitive drop on the main table
			String sql = "DROP TABLE " + tableName;
			runSQLCommand(sql);
		}

		private void deleteRecordFromDataBase(int recordID) {
			String sql = "DELETE FROM " + tableName + " WHERE RECORDID = " + Integer.toString(recordID);
			runSQLCommand(sql);
		}

		private void runSQLCommand(String sqlStatement) {
			ZeroFrame.Data.DatabaseController.executeGeneric(sqlStatement);
		}

		private void runSQLUpdate(DataRecord record) {
			//TODO: update record
			if(record.getRecordID() == -1){
				try {
					insertRecord(record);
				} catch (DataSetNotInitializedException e) {
					// Impossible to happen, java still needs it
					e.printStackTrace();
				}
				return;
			}
			String sql = "UPDATE " + tableName + " SET ";
			//Loop through the fileds and add to the sql string
			Iterator<Entry<String, DataTypes>> iterator = fields.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, DataTypes> entry = (Map.Entry<String, DataTypes>) iterator.next();
				try{ //Gotta put it in a try for the FieldNotFoundException
					switch (entry.getValue()) {
						case INTEGER:
							sql += entry.getKey() + "=" + ((IntegerDataType)record.getFieldValue(entry.getKey())).getValue() + ", ";
							break;
						case LONG:
							sql += entry.getKey() + "=" + ((LongDataType)record.getFieldValue(entry.getKey())).getValue() + ", ";
							break;
						case FLOAT:
							sql += entry.getKey() + "=" + ((FloatDataType)record.getFieldValue(entry.getKey())).getValue() + ", ";
							break;
						case DOUBLE:
							sql += entry.getKey() + "=" + ((DoubleDataType)record.getFieldValue(entry.getKey())).getValue() + ", ";
							break;
						case BOOLEAN:
							String textRepresentation = null;
							if(((BooleanDataType)record.getFieldValue(entry.getKey())).getValue()){
								textRepresentation = "true";
							}else{
								textRepresentation = "false";
							}
							sql += entry.getKey() + "=" + "'" + textRepresentation + "', ";
							break;
						case STRING:
							sql += entry.getKey() + "=" + "'" + ((StringDataType)record.getFieldValue(entry.getKey())).getValue() + "', ";
							break;
						case DATA_SET:
							//TODO: Add functionality for datasets existing as fields in records							
							break;
						default:
							break;
					}
				}catch(FieldNotFoundException exception){
					//would never actually happen
				}
			}
			int lastIdx = sql.length() - 2;
		    sql = sql.substring(0, lastIdx);
			sql += " WHERE SYS_RECORD_ID=" + Integer.toString(record.getRecordID());				
			runSQLCommand(sql);
		}

		private void runSQLLoad(String sqlStatement) throws  DataSetNotInitializedException, FieldNotFoundException, IncompatibleDataTypeException {
			ResultSet result = ZeroFrame.Data.DatabaseController.executeQuery(sqlStatement);
			records.clear();			
			try {
				while (result.next()) {
					DataRecord record = new DataRecord(Integer.parseInt(result.getString("SYS_RECORD_ID")), emptySet, this);
					Iterator<Entry<String, DataTypes>> iterator = fields.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<String, DataTypes> entry = (Map.Entry<String, DataTypes>) iterator.next();
						switch (entry.getValue()) {
							case INTEGER:
								record.fields.put(entry.getKey(), new IntegerDataType(Integer.parseInt(result.getString(entry.getKey())), record));
								break;
							case LONG:
								record.fields.put(entry.getKey(), new LongDataType(Long.parseLong(result.getString(entry.getKey())), record));
								break;
							case FLOAT:
								record.fields.put(entry.getKey(), new FloatDataType(Float.parseFloat(result.getString(entry.getKey())), record));
								break;
							case DOUBLE:
								record.fields.put(entry.getKey(), new DoubleDataType(Double.parseDouble(result.getString(entry.getKey())), record));
								break;
							case BOOLEAN:
								record.fields.put(entry.getKey(), new BooleanDataType(Boolean.parseBoolean(result.getString(entry.getKey())), record));
								break;
							case STRING:
								record.fields.put(entry.getKey(), new StringDataType(result.getString(entry.getKey()), record));
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
			recordPointer = 0;
		}
		
		protected String cleanStringForDatabase(String toBeCleaned){
			return toBeCleaned.replaceAll("[^a-zA-Z0-9]", "");
		}

		/**
		 * A dataset must be initialized in order store data and retrieve data
		 * stored previously.
		 */
		public void initialize() {
			if(checkTableExists()){
			 	//table exists so do... nothing
			}else{
				//table has not yet been created... so create it
				String sql = "CREATE TABLE " + tableName + " (SYS_RECORD_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)";
				//Loop through the fileds and add to the sql string
				Iterator<Entry<String, DataTypes>> iterator = fields.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, DataTypes> entry = (Map.Entry<String, DataTypes>) iterator.next();
					switch (entry.getValue()) {
						case INTEGER:
							sql += ", " + entry.getKey() + " INTEGER";
							break;
						case LONG:
							sql += ", " + entry.getKey() + " BIGINT";
							break;
						case FLOAT:
							sql += ", " + entry.getKey() + " FLOAT";
							break;
						case DOUBLE:
							sql += ", " + entry.getKey() + " DOUBLE";
							break;
						case BOOLEAN:
							sql += ", " + entry.getKey() + " VARCHAR(5)";
							break;
						case STRING:
							sql += ", " + entry.getKey() + " LONG VARCHAR";
							break;
						case DATA_SET:
							//TODO: Add functionality for datasets existing as fields in records							
							break;
						default:
							break;
					}
				}
			sql += ")";
			runSQLCommand(sql);
			}
			initialized = true;
		}

	}

	public class DataRecord {

		protected HashMap<String, DataType> fields = new HashMap<String, DataType>();
		protected int recordid = -1;
		private DataSet parent = null;

		protected DataRecord(int recordID, HashMap<String, DataType> emptySet, DataSet parentDataSet) {
			recordid = recordID;
			fields = emptySet;
			parent = parentDataSet;
			//loop through fields and be sure that all types are associated with this record
			Iterator<Entry<String, DataType>> iterator = fields.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, DataType> entry = (Map.Entry<String, DataType>) iterator.next();
				entry.getValue().parent = this;
			}
			
		}
		
		protected void update(){
			if(parent != null){
				try {
					parent.updateRecord(this);
				} catch (DataSetNotInitializedException e) {
					//Being that only a data set can create a data record and you cant get a data record
					//from an uninitialized data set, this would never happen, but java freaks without it
					//so yeah....
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		public int getRecordID() {
			return recordid;
		}

		public HashMap<String, DataType> getFieldMap() {
			return fields;
		}

		public DataType getFieldValue(String fieldName)	throws FieldNotFoundException {
			fieldName = parent.cleanStringForDatabase(fieldName);
			if (fields.containsKey(fieldName)) {
				return fields.get(fieldName);
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public IntegerDataType getIntegerFieldValue(String fieldName) throws IncompatibleDataTypeException, FieldNotFoundException{
			fieldName = parent.cleanStringForDatabase(fieldName);
			if (fields.containsKey(fieldName)) {
				if(fields.get(fieldName).getType() != DataTypes.INTEGER){
					throw new IncompatibleDataTypeException(DataTypes.INTEGER, fields.get(fieldName).getType());
				}else{
					return (IntegerDataType)fields.get(fieldName);
				}
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public LongDataType getLongFieldValue(String fieldName) throws IncompatibleDataTypeException, FieldNotFoundException{
			fieldName = parent.cleanStringForDatabase(fieldName);
			if (fields.containsKey(fieldName)) {
				if(fields.get(fieldName).getType() != DataTypes.LONG){
					throw new IncompatibleDataTypeException(DataTypes.LONG, fields.get(fieldName).getType());
				}else{
					return (LongDataType)fields.get(fieldName);
				}
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public FloatDataType getFloatFieldValue(String fieldName) throws IncompatibleDataTypeException, FieldNotFoundException{
			fieldName = parent.cleanStringForDatabase(fieldName);
			if (fields.containsKey(fieldName)) {
				if(fields.get(fieldName).getType() != DataTypes.FLOAT){
					throw new IncompatibleDataTypeException(DataTypes.FLOAT, fields.get(fieldName).getType());
				}else{
					return (FloatDataType)fields.get(fieldName);
				}
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public DoubleDataType getDoubleFieldValue(String fieldName) throws IncompatibleDataTypeException, FieldNotFoundException{
			fieldName = parent.cleanStringForDatabase(fieldName);
			if (fields.containsKey(fieldName)) {
				if(fields.get(fieldName).getType() != DataTypes.DOUBLE){
					throw new IncompatibleDataTypeException(DataTypes.DOUBLE, fields.get(fieldName).getType());
				}else{
					return (DoubleDataType)fields.get(fieldName);
				}
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public BooleanDataType getBooleanFieldValue(String fieldName) throws IncompatibleDataTypeException, FieldNotFoundException{
			fieldName = parent.cleanStringForDatabase(fieldName);
			if (fields.containsKey(fieldName)) {
				if(fields.get(fieldName).getType() != DataTypes.BOOLEAN){
					throw new IncompatibleDataTypeException(DataTypes.BOOLEAN, fields.get(fieldName).getType());
				}else{
					return (BooleanDataType)fields.get(fieldName);
				}
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public StringDataType getStringFieldValue(String fieldName) throws IncompatibleDataTypeException, FieldNotFoundException{
			fieldName = parent.cleanStringForDatabase(fieldName);
			if (fields.containsKey(fieldName)) {
				if(fields.get(fieldName).getType() != DataTypes.STRING){
					throw new IncompatibleDataTypeException(DataTypes.STRING, fields.get(fieldName).getType());
				}else{
					return (StringDataType)fields.get(fieldName);
				}
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public DataSetDataType getDataSetFieldValue(String fieldName) throws IncompatibleDataTypeException, FieldNotFoundException{
			fieldName = parent.cleanStringForDatabase(fieldName);
			if (fields.containsKey(fieldName)) {
				if(fields.get(fieldName).getType() != DataTypes.DATA_SET){
					throw new IncompatibleDataTypeException(DataTypes.DATA_SET, fields.get(fieldName).getType());
				}else{
					return (DataSetDataType)fields.get(fieldName);
				}
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
		
		public DataTypes getFieldType(String fieldName)throws FieldNotFoundException {
			if (fields.containsKey(fieldName)) {
				return fields.get(fieldName).getType();
			} else {
				throw new FieldNotFoundException(fieldName);
			}
		}
	}
	
	//To be used later for more advanced filtering, not usable yet, so it stays private
	private class Filter{
		
		protected ArrayList<String> fields = new ArrayList<String>();
		
		private Filter(){
			
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
		
		public IntegerDataType(DataRecord parentRecord){
			parent = parentRecord;
		}
		
		public IntegerDataType(int intValue, DataRecord parentRecord){
			value = intValue;
			set = true;
			parent = parentRecord;
		}
		
		public void setValue(int intValue){
			value = intValue;
			set = true;
			if(parent != null){
				update();
			}
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
		
		public LongDataType(DataRecord parentRecord){
			parent = parentRecord;
		}
		
		public LongDataType(Long longValue, DataRecord parentRecord){
			value = longValue;
			set = true;
			parent = parentRecord;
		}
		
		public Long getValue(){
			return value;
		}
		
		public void setValue(Long longValue){
			value = longValue;
			set = true;
			if(parent != null){
				update();
			}
		}
	
	}
	
	public class FloatDataType extends DataType{
		
		private float value;
		
		@Override
		public DataTypes getType() {
			return DataTypes.FLOAT;
		}
		
		public FloatDataType(){}
		
		public FloatDataType(DataRecord parentRecord){
			parent = parentRecord;
		}
		
		public FloatDataType(Float floatValue, DataRecord parentRecord){
			value = floatValue;
			set = true;
			parent = parentRecord;
		}
		
		public void setValue(Float floatValue){
			value = floatValue;
			set = true;
			if(parent != null){
				update();
			}
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
		
		public DoubleDataType(DataRecord parentRecord){
			parent = parentRecord;
		}
		
		public DoubleDataType(double doubleValue, DataRecord parentRecord){
			value = doubleValue;
			set = true;
			parent = parentRecord;
		}
		
		public void setValue(double doubleValue){
			value = doubleValue;
			set = true;
			if(parent != null){
				update();
			}
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
		
		public BooleanDataType(DataRecord parentRecord){
			parent = parentRecord;
		}
		
		public BooleanDataType(boolean boolValue, DataRecord parentRecord){
			value = boolValue;
			set = true;
			parent = parentRecord;
		}
		
		public void setValue(boolean boolValue){
			value = boolValue;
			set = true;
			if(parent != null){
				update();
			}
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
		
		public StringDataType(DataRecord parentRecord){
			parent = parentRecord;
		}
		
		public StringDataType(String stringValue, DataRecord parentRecord){
			value = stringValue;
			set = true;
			parent = parentRecord;
		}
		
		public void setValue(String stringValue){
			value = stringValue;
			set = true;
			if(parent != null){
				update();
			}
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
		
		public DataSetDataType(DataRecord parentRecord){
			parent = parentRecord;
		}
		
		public DataSetDataType(DataSet dataSet, DataRecord parentRecord){
			value = dataSet;
			set = true;
			parent = parentRecord;
		}
		
		public void setDataSet(DataSet dataSet){
			value = dataSet;
			set = true;
			if(parent != null){
				update();
			}
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
	
	public class DataSetLockedException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5L;
		
		public DataSetLockedException(){
			super("An attempt was made to alter the data set after the set was initialized!");
		}
	}
	
	public class DataSetNotInitializedException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6L;
		
		public DataSetNotInitializedException(){
			super("An attempt to alter the data set was performed without the data set being initialized!");
		}
	}
	
	public class FilterValueNotSetException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 7L;
		
		public FilterValueNotSetException(){
			super("The passed in filter DataType object value is not set!");
		}
		
	}
}
