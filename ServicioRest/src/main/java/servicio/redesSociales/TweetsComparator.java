package servicio.redesSociales;

import java.util.Comparator;

import twitter4j.Status;

public class TweetsComparator implements Comparator<Status> {

	@Override
	public int compare(Status o1, Status o2) {
		int o1Acum = o1.getRetweetCount() + o1.getFavoriteCount();
		int o2Acum = o2.getRetweetCount() + o2.getFavoriteCount();
		
		if (o1Acum < o2Acum)
			return -1;
		if (o1Acum > o2Acum)
			return 1;
		return 0;
	}

}
