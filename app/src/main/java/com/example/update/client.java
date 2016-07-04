package com.example.update;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.DataInputStream;

import android.util.Log;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;





public class client extends Thread{

	public static String banben="beiyou";
	public boolean xiazai=false;
	public Handler handler;
	public boolean unzip;

	private String SDPATH = Environment.getExternalStorageDirectory() + "/";


	public client(String b,Handler h) {
		banben=b;
		handler=h;
	}



	public void run()
	{
		//String storefilepath = "E://pk//";


		Socket s = null;
		File file = null;

		/** 与服务器建立连接 */
		try {
			s = new Socket("192.168.0.107", 8880);
			System.out.println("connected to server");
		} catch (Exception e) {

			System.out.println("未连接到服务器");
			Message msg = handler.obtainMessage();
			msg.what = 2;
			handler.sendMessage(msg);
			//	e.printStackTrace();


		}
		try {
			PrintStream ps = new PrintStream(s.getOutputStream());
			ps.println("111/#" + banben + "/#" + "nothing");
			ps.flush();
		} catch (IOException e) {
			System.out.println("服务器连接中断");
			Log.v("text", "2");
		}
		// 接收type+length
		String comm = null;

		try {
			InputStreamReader isr = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			comm = br.readLine();
		} catch (IOException e) {
			System.out.println("服务器与客户端断开连接");
			Log.v("text", "3");
			System.out.println("未连接到服务器");
			Message msg = handler.obtainMessage();
			msg.what = 2;
			handler.sendMessage(msg);
		}

		/** 开始解析客户端发送过来的请求命令 */
		int index = comm.indexOf("/#");

		/** 判断协议是否为发送文件的协议 */
		String xieyi = comm.substring(0, index);
		if (!xieyi.equals("111")) {
			System.out.println("服务器收到的协议码不正确");
			Log.v("text", "4");
			return;
		}

		/** 解析出文件的名字和大小 */
		comm = comm.substring(index + 2);
		index = comm.indexOf("/#");
		String filetype = comm.substring(0, index).trim();

		/**
		 * 此处睡眠2s，等待服务器把相关的工作准备好 也是为了保证网络的延迟 读者可自行选择添加此代码
		 * */
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		String localFile = SDPATH + filetype;

		byte[] inputByte = null;
		int length = 0;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		InputStream os = null;
		try {
			try {
				os = s.getInputStream();
				dis = new DataInputStream(os);
				file = new File(localFile);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						System.out.println("服务器端创建文件失败");
						Log.v("text", "5");

					}
				} else {
					/** 在此也可以询问是否覆盖 */
					System.out.println("本路径已存在相同文件，进行覆盖");
				}

				fos = new FileOutputStream(file);
				inputByte = new byte[1024];
				System.out.println("开始接收数据...");


				while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
					fos.write(inputByte, 0, length);
					fos.flush();
				}
				System.out.println("完成接收：" + file);
				Log.v("text", "6");
				s.close();
			}
			finally {
				if (fos != null)
					fos.close();
				if (dis != null)
					dis.close();


			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		File file1 = new File(SDPATH);
		unzip zip = new unzip();
		zip.root = file1;
		zip.zipfile = file;

		try {
			unzip=zip.unzip();
			file.delete();
			Message msg = handler.obtainMessage();
			msg.what = 1;
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}

