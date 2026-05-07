package com.weappsinc.watertracker.app.feature.weigh.domain.exception

/** Đã có bản ghi cân cho cùng [epochDay] — mỗi ngày chỉ cho một log. */
class WeighDayAlreadyLoggedException : Exception()
