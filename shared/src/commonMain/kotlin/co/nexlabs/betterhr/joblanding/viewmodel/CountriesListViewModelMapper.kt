package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.joblanding.network.api.home.DynamicPagesListData
import co.nexlabs.betterhr.joblanding.network.api.home.DynamicPagesUIModel
import co.nexlabs.betterhr.joblanding.network.api.request_response.DataResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.DynamicPagesResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.GetCountriesListResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.GetDynamicPagesResponse
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Data

object CountriesListViewModelMapper {

    fun mapResponseToViewModel(response: GetCountriesListResponse): List<Data> {
        return response.data.map { mapDataToCountry(it) }
    }

    private fun mapDataToCountry(data: DataResponse): Data {
        return Data(
            id = data.id,
            countryName = data.name,
        )
    }

    fun mapResponse(response: GetDynamicPagesResponse): List<DynamicPagesListData> {
        return response.data.dynamicPages.map {
            mapDataToDynamic(it)
        }
    }

    private fun mapDataToDynamic(data: DynamicPagesResponse): DynamicPagesListData {
        return DynamicPagesListData(
            id = data.id,
            name = data.name,
        )
    }


}