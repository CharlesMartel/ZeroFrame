package ZeroFrame;

import ZeroFrame.Extensions.Toolbox.DataFactory;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataRecord;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataSet;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataTypes;
import ZeroFrame.Extensions.Toolbox.DataFactory.IntegerDataType;
import ZeroFrame.Extensions.Toolbox.DataFactory.ZeroFrameDataException;

public class Application extends ZeroFrame.Extensions.Module {

	public void run() {
		while (true) {
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

	public Application() {
		DataFactory dFactory = new DataFactory(this);
		DataSet helloset = dFactory.new DataSet("helloset");
		try {
			helloset.fieldAdd("field1", DataTypes.INTEGER);
			helloset.fieldAdd("field2", DataTypes.BOOLEAN);
			helloset.fieldAdd("field3", DataTypes.STRING);
			helloset.initialize();
			helloset.loadAll();

			DataRecord currRecord;
			while ((currRecord = helloset.next()) != null) {
				System.out.println(currRecord.getStringField("field3")
						.getValue());
			}
		} catch (ZeroFrameDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO: Fix this method
	// private void cleanMessageClients(){
	// for(int index = 0; index <
	// ZeroFrame.Networking.ClientManager.messageClients.size(); index++){
	// if(ZeroFrame.Networking.ClientManager.messageClients.get(index).get(0).ClientSocket.isClosed()){
	// ZeroFrame.Networking.ClientManager.clientSockets.remove(index);
	// ZeroFrame.Networking.ClientManager.messageClients.remove(index);
	// }
	// }
	// }
}
