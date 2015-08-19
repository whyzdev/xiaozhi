B.1	聊天机器人，sgtalk（small gtalk）工程代码
GTalkIM类

此类为Gtalk客户端程序。
含有静态变量：
变量名	变量功能
gtalkim	此类的一个实体
chatmanager	程序内的对话管理器，用来获取对话
connection	与gtalk服务器的连接
roster	联系人管理器
chat	当前使用的对话类
userdata	用户资料集
chatlist	包含每个用户对话类的队列

> LetRun类
为gtalkim类中的一个类，用来开启回复线程里面包含ReplyThread 的线程以及startThread函数用来启动ReplyThread线程。含有的变量message和chatt分别需要处理的信息以及对象用户对应的对话类。此类用作获得信息以及回复的并行化，以优化程序回复速度。

MListener类
此类继承MessageListener类，为信息监听器，每当有信息进来则此类将被启动。内容包含创建一个新的LetRun类并将档次所获得的message信息以及信息来源用户所对应的chat类传入LetRun中执行回复流程。

Rlistener类
此类继承RosterLister类，为联系人列表监听器，每当有联系人的添加等变动时就会被启动。当有新用户添加时，此类将重新为所有联系人分配chat类并为该chat类添加MListener，然后将新用户的资料添加到userdata中。用户状态更新时也同样更新一次所有chat类。

savehistory函数
该方法将对话资料写入到“history.txt”的文档中。

主函数main
同样也作为整个工程的入口主函数。函数开始先初始化变量，然后连接到talk.google.com的5222端口，然后使用thaixzhi@gmail.com帐号登陆。然后根据chatmanager获得联系人列表，并设置成自动接收所有邀请的模式。然后更新userdata，并为所有联系人分配chat类及添加监听器。最后，程序进入一个循环，循环中等待一个键盘的输入，输入为q则退出程序，为l则输出联系人列表，s则保存用户信息。

DataProcess类

此类为回复处理主干模块
含有静态变量
变量名	变量功能
mSymbol	一个标点符号集
T1	用来保存问题推荐列表

GetSelfIntro函数
获取一段机器人简单的功能介绍的函数。

GetReply(String inputstring, String from)函数
类中生成回复的方法，返回为一个处理好的回复字符串。inputstring为输入字符串，from为联系人信息。函数首先先初始化各个变量，然后处理from字符串获取用户的电子邮件信息，然后匹配是否返回自我介绍。过后查询UserData中的信息看是否拥有补充信息，若有补充信息且与inputstring相同则使用补充信息替换原信息，若没匹配上则清楚UserData中的补充信息。过后，查询用户资料中看是否上个查询为天气查询，若为天气查询则添加一个“===天气”字符串在输入后面，然后交给WNTClient获取回复。若此时有回复，则设置用户信息为查询了天气预报，然后结束。若还未有回复，则删除“===天气”字眼，然后将inputstring去除标点符号生成nosymbolinput，然后使用nosymbolinput查询语料库，若还未有回复则调用CQAClient中的GetAnswer函数从问答系统服务器获取信息。最后检查看是否已拥有答案，若没有则从语料库获取一个表情符号作为输出，若有问答系统的信息则将问答系统的回答与推荐信息的回答整合，并将推荐信息保留到对应的用户资料中。

AccountData类

用户信息类，用来保存用户信息
变量包括：
变量名	变量功能
name	用户邮件地址
place	用户地点
weather	是否上个查询为天气
lastquestion	上个查询内容
answerset	推荐信息与相似问题列表
index	序号

SaveQuery函数
根据问答系统的两个输出，将问题推荐信息及相似问题列表提取出来并存到answerset中。

GetWeather函数
返回weather值。

GetPlace函数
返回place值。

clearquery函数
清除lastquestion及answerset。

SetWeather函数
设置weather值。
ChatDtbase类

查询语料库所用类。

CheckDatabase函数
查询语料库，根据字符串获取匹配。

GetEmoticon函数
查询语料库，获取一个表情符号。

CQAClient类

查询问答系统类。

GetAnswer函数
使用socket连接问答系统，并获取回复，回复为一个JSONObject。然后将JSONObject中所要用到的信息提取出来，然后去除一些html中的表达符号，替换成正常的表达形式。

WNTClient类

获取天气预报，新闻推荐及翻译功能的类。

GetWNT
使用socket连接，获取天气，新闻及翻译信息，返回的是一段可以直接输出的字符串。
?
B.2 天气，新闻及翻译，WNTAgent(Weather,News,Translate Agent)工程

WNTServer类

此类的服务端，与聊天机器人连接。

StartServer函数
启动socket服务，开始等待输入，接收到输入后调用MainAgent中的GetReply方法获取信息然后回复。

MainAgent类

此服务的主干处理类。

GetReply函数
分别调用WeatherManager,NewsManager及TranslateManager获取信息，然后返回。在调用WeatherManager后去除天气的标识。

WeatherManager类

天气信息处理的类。

GetWeather函数
先将输入翻译成中文，然后调用分词系统，，然后根据分词系统给出的信息判断是否拥有城市名及“天气”字眼，判断是否为天气预报类型的查询，若是则将所有存在的城市名称保留。然后将城市名称通过翻译模块翻译成英文，查询google天气，若没有结果则使用中文的城市名查询中国天气网，最后返回结果。

GetGoogleWeather类

从google天气中获取天气预报的类。


getWeather函数
将城市名称置入网址中获取天气预报信息，保存在一个文件中，天气预报信息为xml格式。将该文件读入Document类，获得xml树并从中获取所需的信息，最后删除文件并将获得的信息处理成字符串返回。

GetCNWeather类

从中国天气网获取天气预报的类。

GetWeather函数
首先从中国天气网中获取8个包含城市信息的网页，然后从中使用正则表达式寻找匹配城市的名称及信息。将信息提取出来后处理成一个字符串然后返回。

NewsManager类

获取新闻信息的类。

GetNews函数
匹配正则表达式，看是否为新闻，然后从网站获取新闻