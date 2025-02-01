package domain

interface EventHandler<E: Event> {
	fun handle(event: E)
}