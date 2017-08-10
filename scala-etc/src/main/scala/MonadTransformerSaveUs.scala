import cats.implicits._
import cats.data.OptionT

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

object MonadTransformerSaveUs {

  case class UserDto(id: Long)
  case class User(id: Long)
  case class Address()
  case class Order(id: Long, userId: Long, itemId: Long)
  case class OrderItem(id: Long, orderId: Long)
  case class ItemOption(id: Long, itemId: Long)
  case class Item(id: Long)
  type Device = String

  def getId: Long = math.random().toLong

  // 다양한 API가 존재한다.
  /** 1 : 1, CPU 연산만 변형하는 연산들 */
  def toUserDto(a: User): UserDto = UserDto(a.id)

  /** 1 : 0..1, 파싱하는 로직같은 경우 */
  def toUserId(userId: String): Option[Long] = Try { userId.toLong }.toOption

  /** 1 : N, 데이터를 N개 변환한다, 뭐가 있을려나? ids 파싱같은 경우 */
  def toIds (ids: String): List[Long] = ids.split(",").map(_.toLong)(collection.breakOut)

  /** 1 : 0..1(with Future) DB에서 select 하는 연산이 있다, 값이 있을수도 없을수도 있다. */
  def findUser(id: Long): Future[Option[User]] = Future.successful(Some(User(id)))
  def findAddress(id: Long): Future[Option[Address]] = Future.successful(Some(Address()))
  def findDevice(userId: Long): Future[Device] = Future.successful("ios")

  /** 1 : 1(with Future) 꼭 있어야 하는 데이터의 경우에는 가능하다. */
  def findOption(itemId: Long): Future[ItemOption] = Future.successful(ItemOption(getId, itemId))
  def findItem(itemId: Long): Future[Item] = Future.successful(Item(itemId))
  def findOrder(userId: Long): Future[Option[Order]] = Future.successful(Some(Order(getId, userId, getId)))

  /** 1 : N(with Future) 외부에서 key를 가지고 여러건의 데이터를 select할 경우가 있다 */
  def findOrders(userId: Long): Future[List[Order]] = Future.successful(List(Order(getId, userId, getId), Order(getId, userId, getId)))
  def findOrderItems(orderId: Long): Future[List[OrderItem]] = Future.successful(List(OrderItem(getId, orderId), OrderItem(getId, orderId)))

  // Future[List[Option[A]]]
  // List(Some(1), Some(2), None) => List(1, 2)

  // 다양하다 해도 이게 다일것 같다.
  // 더 생각나는 사람있으면 이야기 해도 좋다

  // 공식 - 다 너에게 맞추겠어 - 함수 반환값의 타입 모양에 내부에서 호출하는 함수의 타입을 맞추어라!!
  // 그렇기 때문에 구현을 할때 내가 무엇을 갖고 싶은지(반환하고 싶은지)를 먼저 정하고 시작해야한다.
  // 이거 하나면 된것 같다.


  /** Future[ItemOption] 갖고 싶다면 Future[Option[A]]로 반환하는 함수가 있으면 안된다. */
  def onlyFuture: Future[ItemOption] =
    for {
      item <- findItem(10)   // Future[Item]
      option <- findOption(item.id) // Future[ItemOption]
    } yield option


  /** Future[Option[Address]]를 갖고 싶다면 OptionT로 감싸라. */
  // 힘들것이다. 사용하기 어렵다. 짜증난다. 이게 뭐다냐. 쪼사뿔까.
  def optionFuture1: Future[Option[Address]] = {
    (for {
      user: User <- OptionT(findUser(10)) // Future[Option[User]]
      optionAddress: Address <- OptionT(findAddress(user.id)) // Future[Option[Address]]
    } yield optionAddress).value
  }

  def optionFuture2: Future[Option[Device]] = {
//    val userId = toUserId("10") // Option[Long]
//    val user = findUser(userId)  // Future[Option[User]]
//    val userDto = toUserDto(user)  // UserDto
//    val device = findDevice(userDto.id)  // Future[Device]

    (for {
      userId <- OptionT.fromOption[Future](toUserId("10"))
      user <- OptionT(findUser(userId))
      userDto <- OptionT.pure[Future, UserDto](toUserDto(user))
      device <- OptionT.liftF(findDevice(userDto.id))
    } yield device).value
  }
}
