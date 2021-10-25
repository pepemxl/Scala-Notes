public static List<Integer> getEvenNumbersMultpliedBy(List<Integer> numbers){
    List<Integer> results = new ArrayList<>();
    for(int number: numbers){
        if(number%2==0){
            int newNumber  = number*10;
            results.add(newNumber);
        }
    }
    return results;
}