<script lang='ts'>
	import "../app.css";
	import { AppBar } from '@skeletonlabs/skeleton-svelte'
	import { signOut } from '@auth/sveltekit/client';
	import type { PageProps } from "./$types";
	import type { Snippet } from "svelte";
	import { goto } from "$app/navigation";

	type Props = {
		children: Snippet,
		data: PageProps
	}
	let { data, children }: Props = $props();
	const env = import.meta.env.VITE_DECKMASTER_ENVIRONMENT;
</script>

<svelte:head>
	<title>Deckster {env}</title>
</svelte:head>

<AppBar background="bg-gray-950 dark:bg-gray-400">
	{#snippet lead()}
		(icon)
	{/snippet}
	{#snippet children()}
		<strong class="text-xl capitalize text-gray-100 dark:text-gray-950">
			<a 
				href="/"
			>
				Deckster {env}
			</a>
		</strong>
	{/snippet}
	{#snippet trail()}
		{#if data.session}
			{#if data.session.user?.image}
				<span
					style="background-image: url('{data.session.user.image}')"
					class="avatar"></span>
			{/if}
			<span class="signedInText">
				<strong class="text-gray-100 dark:text-gray-950">{data.session?.user?.name ?? "User"}</strong>
			</span>
			<button onclick={() => signOut()} class="btn btn-sm variant-ghost-surface text-gray-100 dark:text-gray-950">Sign out</button>
		{:else}
			<button class="btn btn-sm variant-ghost-surface text-gray-100 dark:text-gray-950" onclick={() => goto("/")}>Sign In</button>
		{/if}

	{/snippet}
</AppBar>

{@render children()}