var x = 0
var y = 0
while(x < 5){
    y = 0
    while(y < 5){
        println(s"$x times $y is ${x*y}")
        y += 1
    }
    x += 1
}