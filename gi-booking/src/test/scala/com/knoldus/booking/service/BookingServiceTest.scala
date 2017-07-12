package com.knoldus.booking.service

import java.sql.Timestamp

import com.knoldus.booking.model.BookingRequest
import com.knoldus.persistence.booking.BookingComponent
import com.knoldus.persistence.user.UserComponent
import com.knoldus.utils.models.{Asset, Booking, User}
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BookingServiceTest extends AsyncFunSuite with Matchers with MockitoSugar {

  private val mockBookingComponent = mock[BookingComponent]
  private val mockUserComponent = mock[UserComponent]

  val bookingService = new BookingService(mockBookingComponent, mockUserComponent)

  val bookingRequest = BookingRequest("123", "a-123", "2017-06-27 18:48:05.123", "2017-06-27 17:48:05.123")

  val timestamp = new Timestamp(123)

  test("add booking functionality when booking gets added successfully") {

    when(mockBookingComponent.insert(any[Booking])).thenReturn(Future.successful(1))
    val output = bookingService.addBooking(bookingRequest)
    output.map { result => result shouldBe 1 }
  }

  test("get list of bookings by user id") {
    val booking = Booking("id-123", "user-123", "asset-123", None, None, None, None, "booked",
      None, timestamp, timestamp, timestamp, None)
    val asset = Asset("asset-123", "projector1", "projector1", "projector", true, timestamp, timestamp)
    when(mockBookingComponent.getBookingsByUserId("user-123")).thenReturn(Future.successful(List((booking, asset))))
    val output = bookingService.getBookingsByUserId("user-123")
    output.map { bookings => bookings shouldBe List((booking, asset))}
  }

  test("get list of all bookings") {
    val booking = Booking("id-123", "user-123", "asset-123", None, None, None, None, "booked",
      None, timestamp, timestamp, timestamp, None)
    when(mockBookingComponent.getAllBooking).thenReturn(Future.successful(List(booking)))
    val output = bookingService.getAllBooking
    output.map { bookings => bookings shouldBe List(booking)}
  }

  test("whether user is admin successfully") {
    val user = User("id-3", "accessToken123", "emp-id-3", "knol-joy", "anurag@knoldus.com", "knol-password3",
      "admin", timestamp, timestamp)
    when(mockUserComponent.getUserByAccessToken("accessToken123")).thenReturn(Future.successful(Some(user)))
    val output = bookingService.isAdmin("accessToken123")
    output.map { bookings => bookings shouldBe true}
  }

  test("whether user is not admin") {
    when(mockUserComponent.getUserByAccessToken("accessToken123")).thenReturn(Future.successful(None))
    val output = bookingService.isAdmin("accessToken123")
    output.map { bookings => bookings shouldBe false}
  }

  test("get available assets for booking") {
    val startTime = new Timestamp(123)
    val endTime = new Timestamp(123)
    val asset = Asset("asset-123", "projector1", "projector1", "projector", true, timestamp, timestamp)
    when(mockBookingComponent.getAssetsAvailableForBooking(startTime, endTime,
      "projector")).thenReturn(Future.successful(List(asset)))
    val output = bookingService.getAvailableAssets(123, 123, "projector")
    output.map { assets => assets shouldBe List(asset)}
  }

}
