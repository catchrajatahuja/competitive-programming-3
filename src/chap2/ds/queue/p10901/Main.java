package chap2.ds.queue.p10901;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;

public class Main {
	/**
	 * @param args
	 */
	static InputReader in 		= new InputReader(System.in);
    static OutputWriter out	=	new OutputWriter(System.out);
	static BufferedReader br=null;

	public static void main(String[] args) throws IOException {
		br=new BufferedReader(new InputStreamReader(System.in));
		while(true) {
            if(process()==0) {
            	break;
            }
            
		}
		out.flush();
		out.close();
	}
	
	static String readLine(){
		try {
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static int[] giveArray(String[] str) {
		int[] ret = new int[str.length];
		for(int i = 0; i < str.length; i++) {
			if(str[i].compareTo("") != 0)
				ret[i] = Integer.parseInt(str[i].trim());
		}
		return ret;
	}
	static List<Integer> giveArrayList(String[] str) {
		List<Integer> ret = new ArrayList<>();
		for(int i = 0; i < str.length; i++) {
			if(str[i].compareTo("") != 0)
				ret.add(Integer.parseInt(str[i].trim()));
		}
		return ret;
	}
	static int findClosest (List<Integer> ll, int val, boolean comp) {
		int a = 0, b = ll.size() - 1;
		
		if(ll.size() == 0)
			return -1;
		if(comp && ll.get(0) > val) {
			return -1;
		}
		if(!comp && ll.get(ll.size()-1) < val) {
			return -1;
		}
		while(a != b) {
			int mid = a + b;
			mid /= 2;
			if((b - a) == 1) {
				if(comp) {
					if(ll.get(b) <= val) {
						a = b;
					}
					else {
						b = a;
					}
				}
				else {
					if(ll.get(a) >= val) {
						b = a;
					}
					else {
						a = b;
					}
				}
				break;
			}
			
			boolean v1 = ll.get(mid) < val;
			boolean v2 = ll.get(mid) > val;
			boolean v3 = ll.get(mid) == val;
			if(v3) {
				a = b = mid;
				break;
			}
			if(comp) {
				if(v1) {
					a = mid;
				}
				else {
					b = mid;
				}
			}
			else {
				if(v2) {
					b = mid;
				}
				else {
					a = mid;
				}
			}
		}
		return a;
	}
	static List<String> ans = new ArrayList<>();
	static int process() {
		int cases = in.readInt();
		while(cases-- > 0) {
			int n = in.readInt(), t = in.readInt(), m = in.readInt();
			Queue<Integer> q1 = new LinkedList<>();
			Queue<Integer> q2 = new LinkedList<>();
			int[] arr = new int[m];
			while(m-- > 0) {
				int i = in.readInt();
				int ind = arr.length - 1 - m; 
				arr[ind] = i;
				String bnk = in.readString().trim();
				if(bnk.compareTo("left") == 0) {
					q1.add(ind);
				}
				else {
					q2.add(ind);
				}
			}
			Map<Integer, Integer> km = new TreeMap<>();
			int curr = 0;
			boolean left = true;
			while(q1.size() > 0 || q2.size() > 0) {
				//System.out.println(left + " : " + q1 + " : " + q2);
				if(left) {
					if(q1.size() > 0 && arr[q1.peek()] <= curr) {
						int add = 0;
						while(q1.size() > 0 && arr[q1.peek()] <= curr && add < n) {
							int v = q1.remove();
							km.put(v, curr + t);
							add++;
						}
						curr+= t;
						left = false;
						
					}
					else if(q2.size() > 0 && arr[q2.peek()] <= curr) {
						left = false;
						curr += t;
					}
					else {
						curr = Integer.MAX_VALUE;
						if(q1.size() > 0)
							curr = Math.min(curr, arr[q1.peek()]);
						if(q2.size() > 0)
							curr = Math.min(curr, arr[q2.peek()]);
					}
				}
				else if(!left){
					if(q2.size() > 0 && arr[q2.peek()] <= curr) {
						int add = 0;
						while(q2.size() > 0 && arr[q2.peek()] <= curr && add < n) {
							int v = q2.remove();
							km.put(v, curr + t);
							add++;
						}
						curr+= t;
						left = true;
					}
					else if(q1.size() > 0 && arr[q1.peek()] <= curr) {
						left = true;
						curr += t;
					}
					else {
						curr = Integer.MAX_VALUE;
						if(q1.size() > 0)
							curr = Math.min(curr, arr[q1.peek()]);
						if(q2.size() > 0)
							curr = Math.min(curr, arr[q2.peek()]);
					}
				}
			}
			for(int k : km.keySet()) {
				System.out.println(km.get(k));
			}
			if(cases > 0)
				System.out.println();
		}
		return 0;
	}
}
class InputReader {

	private InputStream stream;
	private byte[] buf = new byte[1024];
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;

	public InputReader(InputStream stream) {
		this.stream = stream;
	}

	public int read() {
		if (numChars == -1)
			throw new InputMismatchException();
		if (curChar >= numChars) {
			curChar = 0;
			try {
				numChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (numChars <= 0)
				return -1;
		}
		return buf[curChar++];
	}

	public int readInt() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		int res = 0;
		do {
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}

	public String readString() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isSpaceChar(c));
		return res.toString();
	}

	public boolean isSpaceChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	public String next() {
		return readString();
	}

	public interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}

class OutputWriter {
	private final PrintWriter writer;

	public OutputWriter(OutputStream outputStream) {
		writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
	}

	public OutputWriter(Writer writer) {
		this.writer = new PrintWriter(writer);
	}

	public void print(Object...objects) {
		for (int i = 0; i < objects.length; i++) {
			if (i != 0)
				writer.print(' ');
			writer.print(objects[i]);
		}
	}

	public void printLine(Object...objects) {
		print(objects);
		writer.println();
	}

	public void close() {
		writer.close();
	}

	public void flush() {
		writer.flush();
	}

	}

class IOUtils {

	public static int[] readIntArray(InputReader in, int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++)
			array[i] = in.readInt();
		return array;
	}

}
