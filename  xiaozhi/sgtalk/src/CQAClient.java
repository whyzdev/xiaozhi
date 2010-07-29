import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class CQAClient {
	public static String[] GetAnswer(String inputstring) {
		//调用服务器
		String[] resultArray={"","",""} ;
		String result = null ;
		try {
			Socket socket= new Socket("60.195.250.72",11306);
				
			ObjectInputStream reader =  new ObjectInputStream(socket.getInputStream());
			
			PrintWriter writer  = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
			
			writer.println("===xiaozhi"+inputstring);
			writer.flush();
			
			//JSON json;
			socket.setSoTimeout(1200000);
			//= reader.readObject().toString();
			System.out.println(result);
			//JSONObject answerStruct = JSONObject.fromObject("{\"cqa\":{\"answerString\":\"#@@#http://wenwen.soso.com/z/q151938661.htm#@@#口臭怎么治疗#@@#口臭是身体某些疾病的反映。口腔和胃肠道疾病、鼻咽部疾病、呼吸道疾病等都可能有关联。吸烟、饮酒、喝咖啡以及经常吃葱、蒜、韭菜等辛辣刺激食物，或嗜好臭豆腐等具有臭味食物的人，也易引起口臭。<br>此外，晚饭吃得过饱，吃了过多的肉、油腻食物或辛热刺激性调料，而且晚餐距睡眠时间太短，睡觉时胃中还存留着过多这类食物等，第二天也易引发口臭。对年轻人来说，心理压力过大，经常性的精神紧张使消化腺尤其是唾液腺分泌减少，导致口干，厌氧菌大量生长，也容易出现口臭。就是工作后才发现自己也有口臭，同事给介绍用清吻搭档，我就是用吃药治好的。\",\"bestAnswer\":true,\"htmlString\":\"口臭是身体某些疾病的反映。口腔和胃肠道疾病、鼻咽部疾病、呼吸道疾病等都可能有关联。吸烟、饮酒、喝咖啡以及经常吃葱、蒜、韭菜等辛辣刺激食物，或嗜好臭豆腐等具有臭味食物的人，也易引起口臭。<br>此外，晚饭吃得过饱，吃了过多的肉、油腻食物或辛热刺激性调料，而且晚餐距睡眠时间太短，睡觉时胃中还存留着过多这类食物等，第二天也易引发口臭。对年轻人来说，心理压力过大，经常性的精神紧张使消化腺尤其是唾液腺分泌减少，导致口干，厌氧菌大量生长，也容易出现口臭。就是工作后才发现自己也有口臭，同事给介绍用清吻搭档，我就是用吃药治好的。\",\"null\":false,\"qcResults\":[\"哪些药物?是药,中药还是牛黄?\r\n哪些文书?是偏方,方剂还是药方?\r\n哪个地方?是武汉,汕头还是菲?\r\n\",\"药\t治疗口臭什么药好？\r\n中药\t可以治疗口臭中药有哪些\r\n牛黄\t牛黄清胃丸能治疗口臭吗?\r\n偏方\t有什么偏方治疗口臭\r\n方剂\t治疗口臭的中药方剂\r\n药方\t求一副中药治疗口臭的药方\r\n武汉\t武汉 哪家医院治疗 口臭 比较好？\r\n汕头\t汕头金平区有治疗口臭的医院吗\r\n菲\t口腔溃疡|口臭治疗丝菲口臭组合\r\n\"],\"question\":\"口臭怎么治疗\",\"questionUrl\":\"http://wenwen.soso.com/z/q151938661.htm\",\"similarQuestions\":[\"口臭怎么治疗\",\"怎么治疗口臭\",\"怎么治疗口臭？\",\"怎么治疗口臭\",\"口臭怎么治疗\"],\"urls\":[\"http://wenwen.soso.com/z/q151938661.htm\",\"http://wenwen.soso.com/z/q158737767.htm\",\"http://zhidao.baidu.com/question/49368901.html\",\"http://zhidao.baidu.com/question/35296731.html\",\"http://zhidao.baidu.com/question/106432236.html\"]}}");
			JSONObject answerStruct = JSONObject.fromObject(reader.readObject().toString());
			//String cqa = null;
			System.out.println(answerStruct.toString()); 
			if(answerStruct.containsKey("cqa")){
				JSONObject cqaAnswerStruct = answerStruct.getJSONObject("cqa");
				if(cqaAnswerStruct.containsKey("htmlString")){
					result=cqaAnswerStruct.getString("htmlString").toString().replaceAll("\n", "\r\n");
				}
				if(result.contains("（回答过长，具体请见下边提供的链接）")){
					result=result+"\r\n\r\n"+cqaAnswerStruct.getString("questionUrl");
				}
				if(cqaAnswerStruct.containsKey("qcResults")){
					JSONArray qcResultsArray = cqaAnswerStruct.getJSONArray("qcResults");
					if(!qcResultsArray.isEmpty()){
						resultArray[1]=qcResultsArray.getString(0);
						resultArray[2]=qcResultsArray.getString(1);
						
					}
				}
				
				String product="";
				System.out.println("product:\r\n\r\n");
				if(inputstring.matches("产品.*")){
					if(cqaAnswerStruct.containsKey("similarQuestions")){
						JSONArray sqArray=cqaAnswerStruct.getJSONArray("similarQuestions");
						if(!sqArray.isEmpty()){
							for(int i=0;i<10;i++){
								if(sqArray.size()>i){
									if(sqArray.getString(i).contains("产品")){
										product=product+"\r\n"+sqArray.getString(i).substring(3);
										//System.out.println(product);
									}
								}
							}
						}
					}
					if(!product.equals("")){
						result=result+"\r\n\r\n"+"类似产品有：\r\n"+product+"\r\n……";
					}
				}
			}
			
			
			
			else{
				System.out.println("return null");
				return null;
			}
			//清除带<br>的回复
			if(result.contains("<br>")){
				result=result.replaceAll("<br>", "\r\n");
			}
			if(result.contains("<pre>")){
				result=result.replaceAll("<pre>", "");
			}
			if(result.contains("</pre>")){
				result=result.replaceAll("</pre>", "");
			}
			if(result.contains("</br>")){
				result=result.replaceAll("</br>", "\r\n");
			}
			if(result.contains("<br/>")){
				result=result.replaceAll("<br/>", "\r\n");
			}
			if(result.contains("\r\n\r\n")){
				result=result.replaceAll("\r\n\r\n", "\r\n");
				result=result.replaceAll("\r\n\r\n\r\n", "\r\n\r\n");
				//result=result.replaceAll("\r\n\r\n", "\r\n");
			}
			if(result.contains("</a>")){
				result=result.replaceAll("</a>", "");
			}
			if(result.contains("</p>")){
				result=result.replaceAll("</p>", "");
			}
			if(result.contains("<p>")){
				result=result.replaceAll("<p>", "");
			}
			if(result.contains("<strong>")){
				result=result.replaceAll("</strong>", "");
			}
			if(result.contains("</strong>")){
				result=result.replaceAll("</strong>", "");
			}
			if(result.matches("<a href=\"")){
				result=result.replaceAll("<a href=\"", "");
			}
			if(result.matches("\" target=\"_blank\">")){
				result=result.replaceAll("\" target=\"_blank\">", "");
			}
			//String a = reader.readObject().toString();
			socket.close();
			reader.close();
			writer.close();
			resultArray[0]=result;
			
			//return cqa;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultArray;
	}
}
