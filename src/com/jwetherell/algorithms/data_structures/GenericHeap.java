

import java.util.ArrayList;
import java.util.Comparator;

public class genericHeap<T> {
	ArrayList<T> data = new ArrayList<>();
	Comparator<T> ctor;
	
	public genericHeap(Comparator<T> ctor) {
		this.ctor=ctor;
	}

	public void add(T value) {
		data.add(value);
		upimplyfy(data.size() - 1);
	}

	private void upimplyfy(int i) {
		if (i == 0)
			return;
		int p = (i - 1) / 2;
		if (higherP(i, p)) {
			swap(i, p);
			upimplyfy(p);
		}
		return;
	}

	private boolean higherP(int i, int p) {
		T ith = data.get(i);
		T pth = data.get(p);
		if(ctor.compare(ith, pth)>0){
			return true;
		}
		else
			return false;
	}

	private void swap(int i, int j) {
		T ith = data.get(i);
		T jth = data.get(j);
		data.set(i, jth);
		data.set(j, ith);
		return;
	}

	public void display() {
		System.out.println(data);
	}

	public T highestP() {
		return data.get(0);
	}

	public int size() {
		return data.size();
	}

	public boolean isempty() {
		return data.isEmpty();
	}

	public T removeH() {
		T toreturn = data.get(0);
		swap(0, data.size() - 1);
		data.remove(data.size() - 1);
		downimplyfy(0);
		return toreturn;
	}

	private void downimplyfy(int i) {
		int p = 2 * i + 1;
		int max = i;
		if (p < data.size() && higherP(i, p) == false) {
			max = p;
			swap(i, p);
		}
		p = 2 * i + 2;
		if (p < data.size() && higherP(i, p) == false) {
			max = p;
			swap(i, p);
		}
		if (max != i)
			downimplyfy(max);
	}
	
	public void update(T v){
		int indx=0;
		for(int i=0;i<data.size();i++){
			if(data.get(i)==v){
				indx=i;
				break;
			}
		}
		upimplyfy(indx);
		downimplyfy(indx);
		return ;
	}
	
}
