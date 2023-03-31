<script lang="ts">
	import { useApi } from '../utils/useApi';
	import type { ApiResponse, Query, GenreResponse } from '../types';
	import { onMount } from 'svelte';
	import FilmsTable from '../components/FilmsTable.svelte';
	import Categories from '../components/Categories.svelte';
	import Pagination from '../components/Pagination.svelte';

	const { getFilmsByQuery, getFilmsById, getGenres } = useApi();
	let apiResponse: ApiResponse = { count: 0, films: [] };
	let genres: GenreResponse = [];

	onMount(async () => {
		genres = await getGenres(query.query);
		apiResponse = await getFilmsByQuery(query);
	});

	const query: Query = { page: 1, rows: 5, query: { text: '', genre: [] } };
	let id = '';

	function setQueryText(e: InputEvent) {
		query.query.text = (e.target as HTMLInputElement).value;
	}

	function setId(e: InputEvent) {
		id = (e.target as HTMLInputElement).value;
	}

	async function setPage(e: CustomEvent<number>) {
		query.page = e.detail;
		apiResponse = await getFilmsByQuery(query);
	}

	function manageGenres(e: CustomEvent<{ genre: string; selected: boolean }>) {
		const { genre, selected } = e.detail;
		if (selected) {
			query.query.genre.push(genre);
		} else {
			query.query.genre = query.query.genre.filter((g) => g != genre);
		}
	}

	async function fetchFilmsByQuery() {
		query.page = 1;
		await Promise.all<ApiResponse | GenreResponse>([
			getFilmsByQuery(query),
			getGenres(query.query)
		]).then((r) => {
			apiResponse = r[0] as ApiResponse;
			genres = r[1] as GenreResponse;
		});
	}

	async function fetchFilmsById() {
		await getFilmsById(id).then((r) => (apiResponse = r));
	}
</script>

<div class="container flex">
	<aside class="aside flex-grow-1">
		<Categories {genres} selectedGenres={query.query.genre} on:select={manageGenres} />
	</aside>
	<main class="flex-grow-0">
		<form class="flex gap-20 margin-0" on:submit|preventDefault={fetchFilmsByQuery}>
			<input on:change={setQueryText} type="text" placeholder="genre / director / film name" />
			<button style="max-width: 200px" type="submit">Search</button>
		</form>
		<form class="flex gap-20 justify-center margin-0" on:submit|preventDefault={fetchFilmsById}>
			<input style="max-width: 300px" on:change={setId} type="text" placeholder="find by id" />
			<button style="max-width: 200px" type="submit">Search</button>
		</form>
		<div>
			{#if !apiResponse}
				<progress />
			{:else}
				<FilmsTable films={apiResponse.films} />
			{/if}
		</div>
		<Pagination
			amount={apiResponse.count}
			currentPage={query.page}
			rows={query.rows}
			on:pageChange={setPage}
		/>
	</main>
</div>

<style>
	.aside {
		height: 70vh;
		width: 300px;
		overflow: auto;
		background-color: rgba(0, 0, 0, 0.2);
	}
</style>
