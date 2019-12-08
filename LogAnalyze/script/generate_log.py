import random
import time

#创建url访问数组class/112，数字代表的是实战课程id
url_paths = [
 	"class/112.html",
 	"class/128.html",
 	"class/145.html",
 	"class/146.html",
 	"class/500.html",
 	"class/250.html",
 	"class/131.html",
 	"class/130.html",
 	"class/271.html",
 	"class/127.html",
 	"learn/821",
 	"learn/823",
 	"learn/987",
 	"learn/500",
 	"course/list"
]

#创建ip数组，随机选择4个数字作为ip如132.168.30.87
ip_slices = [132,156,124,10,29,167,143,187,30,46,55,63,72,87,98,168,192,134,111,54,64,110,43]

#搜索引擎访问数组{query}代表搜索关键字
http_referers = [
 	"http://www.baidu.com/s?wd={query}",
 	"https://www.sogou.com/web?query={query}",
 	"http://cn.bing.com/search?q={query}",
 	"https://search.yahoo.com/search?p={query}",
]

#搜索关键字数组
search_keyword = [
 	"Spark SQL实战",
 	"Hadoop基础",
 	"Storm实战",
 	"Spark Streaming实战",
 	"Java从入门到放弃",
 	"SpringBoot实战",
 	"Linux进阶",
 	"Vue.js"
]

#状态码数组
status_codes = ["200","404","500","403"]

#随机选择一个url
def sample_url():
	return random.sample(url_paths, 1)[0]

#随机组合一个ip
def sample_ip():
	slice = random.sample(ip_slices , 4)
	return ".".join([str(item) for item in slice])

#随机产生一个搜索url
def sample_referer():
 	#一半的概率会产生非法url，用于模拟非法的用户日志
 	if random.uniform(0, 1) > 0.5:
 		return "-"

 	refer_str = random.sample(http_referers, 1)
 	query_str = random.sample(search_keyword, 1)
 	return refer_str[0].format(query=query_str[0])

#随机产生一个数组
def sample_status_code():
 	return random.sample(status_codes, 1)[0]

#组合以上的内容，产生一条简单的用户访问日志
def generate_log(count = 10):

 	#获取本机时间并将其作为访问时间写进访问日志中
 	time_str = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())

 	#存储日志的目标文件(换成你自己的)
 	f = open("./click.log","w+")

 	#组合用户日志
 	while count >= 1:
 		query_log = "{ip}\t{local_time}\t\"GET /{url} HTTP/1.1\"\t{status_code}\t{referer}".format(url=sample_url(), ip=sample_ip(), referer=sample_referer(), status_code=sample_status_code(),local_time=time_str)

 		f.write(query_log + "\n")

 		count = count - 1

#执行main，每次产生100条用户日志
if __name__ == '__main__':
 	generate_log(100)
