import type { ApiResponse, Film, GenreResponse, Query } from '../types';

export const useApi = function () {
	async function getFilmsById(id: string) {
		return fetch(`/api/film/${id}`)
			.then((r) => r.json())
			.then((r: ApiResponse) => r)
			.catch((r) => {
				console.log(r);
				return null;
			});
	}

	async function getFilmsByQuery(q: Query) {
		return fetch(`/api/film`, {
			method: 'post',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(q)
		})
			.then((r) => r.json())
			.then((r: ApiResponse) => r)
			.catch((r) => {
				console.log(r);
				return null;
			});
	}

	async function getGenres(q: Query['query']) {
		return fetch('/api/genres', {
			method: 'post',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(q)
		})
			.then((r) => r.json())
			.then((r: GenreResponse) => r)
			.catch((r) => {
				console.log(r);
				return null;
			});
	}

	async function addFilm(f: Film) {
		return fetch('/api/film/add', {
			method: 'post',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(f)
		})
			.then((r) => {
				if (!r.ok) throw new Error(r.statusText);
			})
			.then(() => alert('Film added'))
			.catch((e) => alert(e));
	}

	return { getFilmsById, getFilmsByQuery, getGenres, addFilm };
};
