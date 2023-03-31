<script lang="ts">
	import type { GenreResponse } from '../types';
	import { createEventDispatcher } from 'svelte';

	export let genres: GenreResponse;
	export let selectedGenres: GenreResponse;

	$: genresKV = genres.map(recordToKV);

	const dispatch = createEventDispatcher<{
		select: { genre: string; selected: boolean };
	}>();

	function recordToKV(r: Record<string, number>) {
		const key = Object.keys(r)[0];
		return { key, value: r[key] };
	}

	function onGenreSelected(e: Event) {
		const el = e.target as HTMLInputElement;
		dispatch('select', { genre: el.value, selected: el.checked });
	}
</script>

<ul>
	{#each genresKV as g}
		<li>
			<label class="flex align-center justify-space-between hover-cursor"
				><input
					type="checkbox"
					on:change={onGenreSelected}
					value={g.key}
					checked={selectedGenres.includes(g.key)}
				/><span>{g.key}</span><span>{g.value}</span></label
			>
		</li>
	{/each}
</ul>
