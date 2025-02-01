package domain

interface Projection<E: Event, M: Model> {
	fun commitAndProject(r: E): M
}