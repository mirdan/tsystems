package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Rat;

public interface RatService {
	void setRat(Rat rat);
	
	double getAverageRat(String game);
}
