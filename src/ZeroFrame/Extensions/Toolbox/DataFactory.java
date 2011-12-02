package ZeroFrame.Extensions.Toolbox;

import java.util.ArrayList;
import java.util.HashMap;
import ZeroFrame.Extensions.Module;

/**
 * A factory for working with datasets, records, and other data objects
 * 
 * @author Hammer
 */
public class DataFactory {

	Module boundModule = null;
	
	public DataFactory(Module bindingModule){
		boundModule = bindingModule;
	}
	
	public enum DataTypes {
		INTEGER, LONG, FLOAT, DOUBLE, BOOLEAN, STRING, IMAGE_JPEG, DATA_SET
	}
	
	public abstract class DataType{
		public abstract DataFactory.DataTypes getType();		
	}

	public DataSet loadDataSet(String name) {
		//create a set prefix based on the bound modules name
		String setPrefix = boundModule.getName().toUpperCase().trim().replace(" ", "");
		return new DataSet(name, setPrefix);
	}

	public class DataSet {

		private String name = null;
		private String prefix = null;
		private HashMap<String, DataType> fields = new HashMap<String, DataType>();
		private ArrayList<DataRecord> records = new ArrayList<DataRecord>();
		private int recordPointer = 0;
		private String tableName = null;

		public DataSet(String datasetName, String datasetPrefix) {
			name = datasetName.trim().toUpperCase().replace(" ", "");
			prefix = datasetPrefix;
			tableName = prefix + name;
		}

		public void fieldAdd(String fieldName, DataType fieldType) {
			String fieldNameParsed = fieldName.trim().toUpperCase().replace(" ", "");
			if (!fields.containsKey(fieldNameParsed)) {
				fields.put(fieldNameParsed, fieldType);
			}
		}

		public void fieldAlter(String fieldName, DataType fieldType) {
			String fieldNameParsed = fieldName.trim().toUpperCase().replace(" ", "");
			if (fields.containsKey(fieldNameParsed)) {
				fields.remove(fieldNameParsed);
				fields.put(fieldNameParsed, fieldType);
			}
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

		public void updateRecord(DataRecord record) {

		}

		public int insertRecord(DataRecord record) {
			return 0;
		}

		public void deleteRecord(DataRecord record) {

		}

		public void getCurrentRecord() {
			
		}

		public boolean next() {
			return true;
		}

		public void getFieldValue() {

		}

		private void checkCreateTable(){
			String sql = "SELECT " + tableName + " FROM SYSTABLES";
		}
		
		private void truncateDB() {
			// TODO: truncate bound tables
		}

		private void eraseDB() {
			// TODO: erase bound tables
		}
		
		private void deleteRecordFromDataBase(int recordID){
			
		}
		
		private void runSQLCommand(String sqlStatement){
			
		}
		
		private void runSQLLoad(String sqlStatement){
			
		}

	}

	public class DataRecord {

		private HashMap<String, Object> fields = new HashMap<String, Object>();
		private HashMap<String, DataType> fieldTypes = new HashMap<String, DataType>();
		private int recordid; 
		
		public DataRecord(){}
		
		public DataRecord(int recordID){
			recordid = recordID;
		}
		
		public HashMap<String, Object> getFieldMap() {
			return fields;
		}

		public void setFieldMap(HashMap<String, Object> fieldMap) {
			fields = fieldMap;
		}

		public HashMap<String, DataType> getFieldTypeMap() {
			return fieldTypes;
		}

		public void setFieldTypeMap(HashMap<String, DataType> fieldTypeMap) {
			fieldTypes = fieldTypeMap;
		}

		public Object getValue(String fieldName) {
			return fields.get(fieldName);
		}
		
		public void setValue(){
			
		}

		public DataType getFieldType(String fieldName) {
			return fieldTypes.get(fieldName);
		}		
	}
}
