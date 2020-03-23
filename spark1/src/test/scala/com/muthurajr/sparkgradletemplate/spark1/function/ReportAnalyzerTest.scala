package com.muthurajr.sparkgradletemplate.spark1.function

import java.sql.Date

import com.muthurajr.sparkgradletemplate.spark1.data.{Customer, Issue, IssueReport}
import org.junit.runner.RunWith
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class ReportAnalyzerTest extends FunSpec {

  describe("Empty data if ") {
    it("no issues are provided in input") {
      //given
      val customer = Customer(1, "first1", "last1")

      //when
      val customerReport = ReportAnalyzer.create(customer, None)

      //then
      assert(customerReport.issue_count.size == 0)
      assert(customerReport.customer_id == 1)
    }
  }

  describe("Valid data ") {
    it("with single status") {
      //given
      val customer = Customer(1, "first1", "last1")
      val issues = Array(Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-01"), "created"),
        Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-02"), "created"))

      //when
      val customerReport = ReportAnalyzer.create(customer, Some(issues))

      //then
      val expectedReport = IssueReport(1, Map("created" -> 2))
      assert(customerReport.equals(expectedReport))
    }

    it("with multiple status") {
      //given
      val customer = Customer(1, "first1", "last1")
      val issues = Array(Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-01"), "solved"),
        Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-02"), "solved"),
        Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-03"), "created"),
        Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-04"), "created"))

      //when
      val customerReport = ReportAnalyzer.create(customer, Some(issues))

      //then
      val expectedReport = IssueReport(1, Map("solved" -> 2, "created" -> 2))
      assert(customerReport.equals(expectedReport))
    }
  }

}
