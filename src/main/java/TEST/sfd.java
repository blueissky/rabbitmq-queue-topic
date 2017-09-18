package TEST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class sfd {
	@Test
	public void aa(){
		List a=new ArrayList();
		List b=new ArrayList();

		Map aa=new HashMap();
		Map bb=new HashMap();
		
		aa.put("name", "wang");
		bb.put("name", "zhang");
		
		a.add(aa);
		b.add(bb);
		a.addAll(b);
		
		System.out.println(a.toString());
	}
}
