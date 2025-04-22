<script lang='ts'>
    interface Props {
        card: any;
        selected: boolean;
        onClick: any;
        selectionIndex: number;
    }

    let { card, selected, onClick, selectionIndex }: Props = $props();
</script>

<button class="pt-6 pr-4" onclick={() => onClick(card)} type="button">
    <div class="block card card-hover {card.type.name} {selected ? 'border-4 font-bold' : 'border-2 font-normal'} hover:border-4">
        <header class="card-header"></header>
        <section class="p-4">{card.content}</section>
        <footer class="card-footer bottom-0">{selectionIndex > 0 ? selectionIndex : ''}</footer>
    </div>
</button>

<style lang="postcss">
    .card {
        display: flex;
		flex-direction: column;
		flex-wrap: wrap;
        width: 200px;
        height: 280px;
        border-style: solid;
        border-radius: 13px;
        font-family:  Neue LT,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,sans-serif;
        font-size: 20px;
        line-height: 26px;
    }
	.Prompt {
        border-color: rgb(255, 255, 255);
        background-color: rgb(0, 0, 0) !important;
        color: rgb(255, 255, 255);
	}

    .Response {
        border-color: rgb(0, 0, 0);
        background-color: rgb(255, 255, 255);
        color: rgb(0, 0, 0);
    }
</style>
