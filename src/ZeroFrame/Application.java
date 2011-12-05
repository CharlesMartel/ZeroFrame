package ZeroFrame;

import ZeroFrame.Extensions.Toolbox.DataFactory;
import ZeroFrame.Extensions.Toolbox.DataFactory.BooleanDataType;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataRecord;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataSet;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataSetLockedException;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataSetNotInitializedException;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataTypes;
import ZeroFrame.Extensions.Toolbox.DataFactory.FieldNotFoundException;
import ZeroFrame.Extensions.Toolbox.DataFactory.IncompatibleDataTypeException;
import ZeroFrame.Extensions.Toolbox.DataFactory.IntegerDataType;
import ZeroFrame.Extensions.Toolbox.DataFactory.StringDataType;

public class Application extends ZeroFrame.Extensions.Module {

	public void run(){
		while(true){
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "HELLO MODULE";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Application(){
		DataFactory dFactory = new DataFactory(this);
		DataSet helloset = dFactory.new DataSet("helloset");
		try {
			helloset.fieldAdd("field1", DataTypes.INTEGER);
			helloset.fieldAdd("field 2", DataTypes.BOOLEAN);
			helloset.fieldAdd("field3", DataTypes.STRING);
			helloset.initialize();
			helloset.loadAll();
			DataRecord currRecord;
			while((currRecord = helloset.next()) != null){
				System.out.println(currRecord.getStringFieldValue("field3").getValue());
			}
			//DataRecord record = helloset.getRecordTemplate();
			//IntegerDataType idt = (IntegerDataType) record.getFieldValue("field1");
			//BooleanDataType bdt = (BooleanDataType) record.getFieldValue("field 2");
			//tringDataType sdt = (StringDataType) record.getFieldValue("field3");
			//idt.setValue(5);
			//bdt.setValue(true);
			//sdt.setValue("hello sean!");
		} catch (DataSetLockedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataSetNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncompatibleDataTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FieldNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//TODO: Fix this method
	//private void cleanMessageClients(){
	//	for(int index = 0; index < ZeroFrame.Networking.ClientManager.messageClients.size(); index++){
	//		if(ZeroFrame.Networking.ClientManager.messageClients.get(index).get(0).ClientSocket.isClosed()){
	//			ZeroFrame.Networking.ClientManager.clientSockets.remove(index);
	//			ZeroFrame.Networking.ClientManager.messageClients.remove(index);
	//		}
	//	}
	//}
}
