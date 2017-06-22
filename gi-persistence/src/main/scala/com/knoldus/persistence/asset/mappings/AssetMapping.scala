package com.knoldus.persistence.asset.mappings

import java.sql.Timestamp
import com.knoldus.persistence._
import com.knoldus.utils.models.Asset

trait AssetMapping {this: DBComponent =>

  import driver.api._
  class AssetMapping(tag: Tag) extends Table[Asset](tag, "asset") {
    def id: Rep[String] = column[String]("id", O.PrimaryKey)
    def name: Rep[String] = column[String]("name")
    def uniqueName: Rep[String] = column[String]("unique_name")
    def assetType: Rep[String] = column[String]("type")
    def isAvailable: Rep[String] = column[String]("is_available")
    def createdAt: Rep[Timestamp] = column[Timestamp]("created_at")
    def lastModifiedAt: Rep[Timestamp] = column[Timestamp]("last_modified_at")

    def * : ProvenShape[Asset] = (
      id,
      name,
      uniqueName,
      assetType,
      isAvailable,
      createdAt,
      lastModifiedAt
      ) <>(Asset.tupled, Asset.unapply)
  }

  val assetInfo: TableQuery[AssetMapping] = TableQuery[AssetMapping]
}
