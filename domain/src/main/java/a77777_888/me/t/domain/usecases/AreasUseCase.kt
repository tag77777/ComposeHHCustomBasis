package a77777_888.me.t.domain.usecases

import a77777_888.me.t.domain.dataentities.IDataProvider

class AreasUseCase {
    companion object {
        suspend fun getAreas(dataProvider: IDataProvider) = dataProvider.getAreas()
    }
}