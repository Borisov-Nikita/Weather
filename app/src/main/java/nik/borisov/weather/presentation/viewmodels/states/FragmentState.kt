package nik.borisov.weather.presentation.viewmodels.states

sealed class FragmentState

object ShowForecastFragment : FragmentState()
object EnterLocationFragment : FragmentState()
object GetLocationFromService : FragmentState()
class ShowErrorFragment(message: String) : FragmentState()
