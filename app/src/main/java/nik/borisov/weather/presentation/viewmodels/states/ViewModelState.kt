package nik.borisov.weather.presentation.viewmodels.states

sealed class ViewModelState

object ShowForecastFragment : ViewModelState()
object ShowLocationFragment : ViewModelState()
object ShowLoading: ViewModelState()
object GetLocationFromService : ViewModelState()
object SetViewModelState : ViewModelState()
class ShowErrorFragment(message: String) : ViewModelState()
