# zookeeper


zookeeper 常见操作及应用场景

# watcher
触发规则
	1. 父节点的变更以及孙节点的变更都不会触发watcher
	2. 而对watcher本身节点以及子节点的变更会触发watcher
		2.1  连接恢复、会话超时会触发所有watcher
		2.2 当前节点的变化会触发getdata的watcher
		2.3 对子节点个数产生影响（前节点的create、delete及子节点的create、delete）的变化触发getchildren的watcher
	2.4 exits的watcher只会在当前节点的set和delete操作触发
触发顺序
	给同一个节点通过不同的方式注册不同的watcher实例（使用不同的对象）
	1、当删除rootpath+childpath节点时，ExistWater跟DataWatcher肯定比ChilrenWatcher先执行，ExistWater跟DataWatcher的顺序是不定的
	2、当修改rootpath+childpath节点数据时，ChilrenWatcher是不会触发的，ExistWater跟DataWatcher执行的顺序跟他们的注册时间有关系
	3、当新增rootpath+childpath节点的时候，DataWatcher是不存在的，ExistWater在ChilrenWatcher之前执行
	另外需要说明的是Watcher的执行时同步的，一定是一个执行完之后再执行另一个的，不是同时进行的，比如执行ExistWater执行完在执行DataWatcher，不会同时执行的
	
注意：多次注册同一个watcher对象，只会触发一次，要多次触发则需多次注册不同对象（多次new，不能只new一次）
		  
