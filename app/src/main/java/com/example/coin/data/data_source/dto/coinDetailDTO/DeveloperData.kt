package com.example.coin.data.data_source.dto.coinDetailDTO

data class DeveloperData(
    val closed_issues: Double,
    val code_additions_deletions_4_weeks: CodeAdditionsDeletions4Weeks,
    val commit_count_4_weeks: Double,
    val forks: Double,
    val last_4_weeks_commit_activity_series: List<Any>,
    val pull_request_contributors: Double,
    val pull_requests_merged: Double,
    val stars: Double,
    val subscribers: Double,
    val total_issues: Double
)