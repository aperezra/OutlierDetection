public class ListWriter implements Runnable
{
    Data myData;
 
    public ListWriter(Data myData)
    {
        super();
        this.myData = myData;
    }
 
    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            myData.add(Thread.currentThread().getName() + " : " + i);
        }
    }
}