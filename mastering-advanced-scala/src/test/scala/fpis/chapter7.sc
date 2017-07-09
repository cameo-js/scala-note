import java.io.{FileNotFoundException, FileReader, IOException}
// 내장제어구문

// 7.1 if 표현식
val status = true
// 명령형 스타일
var value1 = ""
if (status) value1 = "true" else value1 = "false"

// 함수형 스타일
val value2 = if (status) "true" else "false"


// 7.3 for 표현식
// <- 제너레이터
// to, until Range
for (i <- 1 to 4)
  println(i)

for ( i <- 1 until 4)
  println(i)

// 필터링
for (
  i <- 1 to 10
  if i%2 == 0
  if i%3 == 0
  if i%5 == 0
) println(i)

// 중첩순회
def list1 = 1 to 10
def list2(i: Int) = i*100 to i*100+10

// 1.컴파일이 될까?
// 2.결과는 어떻게 찍힐까?
for {
  a <- list1
  if a % 5 == 0
  b <- list2(a)
  if b % 5 == 0
} println(a + ":" +b)


// 변수바인딩
for {
  a <- list1
  if a%5 == 0
  mod = a%5
} println(a + ":mod = " +mod)

// 새로운 컬렉션 만들기
val newList: Seq[Int] = for {
  a <- list1
  if a % 3 == 0
} yield a


// 7.4 try 표현식
// 예외잡기
// 패턴 매치 형태를 채용(15장)
try {
  val f = new FileReader("input.txt")
} catch {
  case ex: FileNotFoundException => println("file not found")
  case ex: IOException => println("io exception")
}

// fanally 절
def f(): Int = try { return 1 } finally { return 2 }
def g(): Int = try { 1 } finally { println("martin"); 2 }

f()
g()
// 결과 값은?

// 7.5 match 표현식
// switch 와 유사
// 자바의 case 문에는 enum이나 정수 타입만 가능
// 스칼라의 case 문에는 어떤 종류의 상수라도 사용가능
// break도 없다
// 15장에서 자세히

// 7.6 break와 continue 문 없이 살기
// 스칼라 아니어도 이렇게 산지 꽤 된거같음

// 7.7 변수 스코프
//val c = 1
//val c = 2
//println(c)


val a = 1

{
  val a = 2
  println(a)
}

println(a)


// 어떻게 출력될까?
val b = 5

{
  val b = 8
  println(b)
}

// 7.8 명령형 코드 리팩토링


