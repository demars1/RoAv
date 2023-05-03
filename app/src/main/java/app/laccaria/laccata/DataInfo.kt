package app.laccaria.laccata

import com.google.gson.annotations.SerializedName

data class DataInfo (
    @SerializedName("geotag")
    val geotag: String?,
    @SerializedName("buyer_name")
    val buyer_name: String?,
    @SerializedName("creative_name")
    val creative_name: String?,
    @SerializedName("af_status")
    val af_status: String?,
    @SerializedName("af_channel")
    val af_channel: String?,
    @SerializedName("ad_id")
    val ad_id: String?,
    @SerializedName("campaign_id")
    val campaign_id: String?,
    @SerializedName("campaign_group_name")
    val campaign_group_name: String?,
    @SerializedName("campaign_group_id")
    val campaign_group_id: String?,
    @SerializedName("adgroup_name")
    val adgroup_name: String?,
    @SerializedName("adgroup_id")
    val adgroup_id: String?,
    @SerializedName("campaign_name")
    val campaign_name: String?,
    @SerializedName("app_id")
    val app_id: String?,
    @SerializedName("device_id")
    val device_id: String?,
    @SerializedName("reserved_1")
    val reserved_1: String?,
    @SerializedName("reserved_2")
    val reserved_2: String?,
    @SerializedName("reserved_3")
    val reserved_3: String?,
    @SerializedName("offer_link")
    val offer_link: String?,
    @SerializedName("package_name")
    val package_name: String?,
)