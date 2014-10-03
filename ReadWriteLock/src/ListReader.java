public class ListReader implements Runnable
{
	Data myData;

	public ListReader(Data myData)
	{
		super();
		this.myData = myData;
	}

	public void run()
	{
		for (int i = 0; i < 10; i++)
		{
			myData.readData();
		}
	}
}