export interface Film {
	id: string;
	name: string;
	directed_by: Array<string>;
	genre: Array<string>;
	initial_release_date: string;
}

export interface ApiResponse {
	count: number;
	films: Array<Film>;
}

export interface Query {
	page: number;
	rows: number;
	query: {
		text: string;
		genre: Array<string>;
	};
}

export type GenreResponse = Array<Record<string, number>>
