<script lang="ts">
	import type { Film } from '../../types';
	import { useApi } from '../../utils/useApi';

	const { addFilm } = useApi();

	const film: Film = {
		id: '',
		name: '',
		directed_by: [],
		genre: [],
		initial_release_date: ''
	};

	async function postFilm(e: SubmitEvent) {
		const form = e.target as HTMLFormElement;
		for (const f of Object.keys(film) as keyof Film) {
			const v = (form.elements.namedItem(f) as HTMLInputElement).value;
			if (film[f] instanceof Array) {
				film[f] = v
					.split(',')
					.map((s) => s.trim())
					.filter(Boolean);
			} else {
				film[f] = v;
			}
		}
		await addFilm(film);
	}
</script>

<form on:submit|preventDefault={postFilm} class="container">
	<input type="text" name="id" placeholder="id" required />
	<input type="text" name="name" placeholder="name" required />
	<input type="text" name="genre" placeholder="genres, separated by comma" required />
	<input type="text" name="directed_by" placeholder="directors, separated by comma" required />
	<input type="date" name="initial_release_date" placeholder="release date" required />
	<button type="submit">Post film</button>
</form>
