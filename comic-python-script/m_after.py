# encoding:UTF-8
import get_comic

# ComicHentai[2016-03-23 17:33:14]/total.json
print("输入total.json的路径")
data = raw_input()
get_comic.multi_thread_after(data)
