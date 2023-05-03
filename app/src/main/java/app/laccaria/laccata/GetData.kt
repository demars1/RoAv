package app.laccaria.laccata


data class GetData(
    val report: Report,
    val success: Boolean
)

data class Report(
    val alias: String,
    val campaign_name: String,
    val domain: String
)