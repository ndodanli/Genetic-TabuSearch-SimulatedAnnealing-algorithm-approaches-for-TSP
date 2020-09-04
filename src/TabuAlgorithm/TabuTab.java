package TabuAlgorithm;


public class TabuTab {
	  int [][] tabuTabTab ;
	    public TabuTab(int amountOfCities){
	        tabuTabTab = new int[amountOfCities][amountOfCities]; 
	    }
	    
	   
	    public void decrementTabu(){
	        for(int i = 0; i<tabuTabTab.length; i++){
	           for(int j = 0; j<tabuTabTab.length; j++){
	            tabuTabTab[i][j]-=tabuTabTab[i][j]<=0?0:1;
	         } 
	        }
	    }
	    
	    public void tabuMove(int city1, int city2){ 
	        tabuTabTab[city1][city2]+= 5;
	        tabuTabTab[city2][city1]+= 5;
	        
	    }
	    
}
