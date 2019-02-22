public class Contact {

	public static String _num;
	public static String _last;
	public static String _first;
	public Contact(String num, String last, String first){
		_num = num;
		_last = last;
		_first = first;
	}
	
	public static String getNumber(){
		return _num;
	}
	
	public static String getLast(){
		return _last;
	}

	public static String getFirst(){
		return _first;
	}

}