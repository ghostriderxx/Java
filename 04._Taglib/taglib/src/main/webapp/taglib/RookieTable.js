const RookieTable = (function(){
	function RookieTable(params){
		const {domid, name, width, height} = params;
		
		const $el = $(`#${domid}`);
		
		// API
		this.getName = () => name;
		this.getWidth = () => width;
		this.getHeight = () => height;
		this.getHTML = () => $el.html();
	}
	
	/**
	 * RookieTable 静态函数
	 */
	const __rookie_tables = {};
	
	RookieTable.get = (name) => {
		return __rookie_tables[name];
	}
	
	RookieTable.set = (name, rookieTable) => {
		__rookie_tables[name] = rookieTable;
	}
	
	return RookieTable;
}())