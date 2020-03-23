package com.muthurajr.sparkgradletemplate.spark1

import java.sql.Date

import com.holdenkarau.spark.testing.DatasetSuiteBase
import com.muthurajr.sparkgradletemplate.spark1.data.{Customer, Issue, IssueReport}
import com.muthurajr.sparkgradletemplate.spark1.encoder.ModelEncoder._
import org.apache.spark.sql.SaveMode
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSpec}

@RunWith(classOf[JUnitRunner])
class ProcessorTest extends FunSpec with DatasetSuiteBase with BeforeAndAfterAll {

  var tempFolder: TemporaryFolder = _
  var directory: String = _

  override def beforeAll() {
    super.beforeAll()
    tempFolder = new TemporaryFolder
    tempFolder.create()
    directory = tempFolder.getRoot.getAbsolutePath
  }

  override def afterAll() {
    super.afterAll()
    tempFolder.delete()
  }

  describe("Valid scenario with ") {
    it("all required args") {
      //given
      val issuePath = s"${directory}/sc1/issue/"
      val customerPath = s"${directory}/sc1/customer/"
      val reportPath = s"${directory}/sc1/report/"

      val issues = spark.createDataset(Array[Issue](Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-01"), "created")))
      issues.write.mode(SaveMode.Overwrite).parquet(issuePath)
      val customer = spark.createDataset(Array[Customer](Customer(1, "first1", "last1")))
      customer.write.mode(SaveMode.Overwrite).parquet(customerPath)

      //when
      Processor.main(Array("-c", customerPath, "-i", issuePath, "-r", reportPath))

      //then
      val expected = spark.createDataset(Array[IssueReport](IssueReport(1, Map("created" -> 1))))
      val actual = spark.read.parquet(reportPath).as[IssueReport]
      assertDatasetEquals(expected.sort("customer_id"), actual.sort("customer_id"))

    }
  }

}
