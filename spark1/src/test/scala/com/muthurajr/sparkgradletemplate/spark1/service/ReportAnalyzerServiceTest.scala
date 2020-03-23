package com.muthurajr.sparkgradletemplate.spark1.service

import java.sql.Date

import com.holdenkarau.spark.testing.DatasetSuiteBase
import com.muthurajr.sparkgradletemplate.spark1.data.{Customer, Issue, IssueReport}
import com.muthurajr.sparkgradletemplate.spark1.encoder.ModelEncoder._
import org.junit.runner.RunWith
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReportAnalyzerServiceTest extends FunSpec with DatasetSuiteBase {

  describe("Valid scenario with ") {
    it("empty customer") {
      //given
      val customer = spark.createDataset(Array[Customer]())
      val issues = spark.createDataset(Array[Issue](Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-01"), "created")))

      //when
      val actualResult = ReportAnalyzerService.process(spark, customer, issues)

      //then
      assert(actualResult.count() == 0)
    }
    it("empty issues") {
      //given
      val customer = spark.createDataset(Array[Customer](Customer(1, "first1", "last1")))
      val issues = spark.createDataset(Array[Issue]())

      //when
      val actualResult = ReportAnalyzerService.process(spark, customer, issues)

      //then
      assert(actualResult.count() == 0)

    }
    it("empty customer and issues") {
      //given
      val customer = spark.createDataset(Array[Customer]())
      val issues = spark.createDataset(Array[Issue]())

      //when
      val actualResult = ReportAnalyzerService.process(spark, customer, issues)

      //then
      assert(actualResult.count() == 0)

    }
    it("valid customers and issues") {
      //given
      val customer = spark.createDataset(Array[Customer](Customer(1, "first1", "last1"),
        Customer(2, "first2", "last2")))
      val issues = spark.createDataset(Array[Issue](Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-01"), "created"),
        Issue(1, 2, "issue1", "description1", Date.valueOf("2019-01-01"), "created"),
        Issue(1, 3, "issue1", "description1", Date.valueOf("2019-01-01"), "solved"),
        Issue(2, 1, "issue1", "description1", Date.valueOf("2019-01-01"), "created")))

      //when
      val actualResult = ReportAnalyzerService.process(spark, customer, issues)

      //then
      val expected = spark.createDataset(Array[IssueReport](IssueReport(1, Map("created" -> 2, "solved" -> 1)),
        IssueReport(2, Map("created" -> 1))
      ))
      assertDatasetEquals(expected.sort("customer_id"), actualResult.sort("customer_id"))

    }

  }


}
