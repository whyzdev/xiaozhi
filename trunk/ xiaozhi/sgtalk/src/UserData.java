import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;



public class UserData {
	//用户资料
	static Vector<AccountData> accountdata;
	
	
	//根据email获取用户需要，查该用户存不存在
	public static int getIndex(String name){
		//System.out.println("for "+name);
		for(int i=0;i<accountdata.size();i++)
		{
			if(accountdata.get(i).name.equals(name))
			{
				//TODO index重复赋值去掉
				accountdata.get(i).index=i;
				//System.out.println("get index="+i);
				return i;
			}
		}
		return -1;
		
	}
	
	//添加新用户资料，不判重
	public static boolean addData(String name){
		
		accountdata.add(new AccountData(name));
		//System.out.println("add "+name);
		for(int i=0;i<accountdata.size();i++)
		{
			//System.out.println(accountdata.get(i).name+" "+accountdata.get(i).index);
		}
		return true;
	}
	
	//从硬盘读取信息
	public static boolean LoadData() throws IOException, ClassNotFoundException{
		
		accountdata=new Vector<AccountData>();
		int asize;
		File f=new File("data.txt"); 
		if(f.exists()){
						
			FileInputStream fis=new FileInputStream("data.txt");
			ObjectInputStream ois=new ObjectInputStream(fis);
			
			asize=ois.readInt();
			//System.out.println("size="+asize);
			
			for(int i=0;i<asize;i++)
			{
				//TODO 和write对照添加新资料
				accountdata.add(new AccountData());
				accountdata.get(i).index=ois.readInt();
				//System.out.println("read index :"+accountdata.get(i).index);
				accountdata.get(i).name=(String) ois.readObject();
				//System.out.println("read name  :"+accountdata.get(i).name);
			}
		}
		
		return false;
	}
	//存取用户资料到硬盘
	public static boolean SaveData() throws IOException{
		
		File f=new File("data.txt"); 
		if(!f.exists()){
			f.createNewFile();
		}
		FileOutputStream fos=new FileOutputStream("data.txt");
		ObjectOutputStream oos=new ObjectOutputStream(fos);
	
		oos.writeInt(accountdata.size());
		for(int i=0;i<accountdata.size();i++)
		{
			//数据写入文件
			//TODO 和read对照添加新资料
			oos.writeInt(accountdata.get(i).index);
			oos.writeObject(accountdata.get(i).name);
		}
		
		return true;
		
	}
}
