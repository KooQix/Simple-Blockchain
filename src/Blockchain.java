
import javax.management.InvalidAttributeValueException;

public class Blockchain {

	private Block lastBlock;

	/**
	 * Create a new blockchain
	 */
	public Blockchain() {
		this.lastBlock = null;
	}

	/**
	 * Copy a blockchain
	 * 
	 * @param blockchain
	 */
	public Blockchain(Blockchain blockchain) {

		Block block = blockchain.getLastBlock();
		this.lastBlock = new Block(block.getId(), block.getData());
	}

	/**
	 * Add a block to the blockchain
	 * 
	 * @param block
	 * @throws InvalidAttributeValueException
	 */
	public void addBlock(String data) throws InvalidAttributeValueException {

		if (lastBlock != null && data.equals(lastBlock.getData())) {
			throw new InvalidAttributeValueException("ERROR: Block is a duplicate");
		}

		int id = this.lastBlock == null ? 0 : this.lastBlock.getId();
		Block block = new Block(id + 1, data);

		// Link new last block with old last block
		block.setPreviousBlock(this.lastBlock);

		// Add the block to the blockchain
		block.mine();
		this.lastBlock = block;
	}

	//////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\

	/**
	 * @return the lastBlock
	 */
	public Block getLastBlock() {
		return lastBlock;
	}

	/**
	 * Get the block where block.id == blockID on the blockchain
	 * 
	 * @param blockId
	 * @return
	 */
	public Block getBlock(int blockId) {
		if (blockId < 1) {
			return null;
		}

		Block block = this.lastBlock;
		int i = 1;
		while (this.lastBlock.getId() - i >= blockId) {
			block = block.getPreviousBlock();
			i++;
		}
		return blockId == block.getId() ? block : null;

	}

	@Override
	public String toString() {
		if (this.lastBlock == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		Block block = this.lastBlock;
		while (block != null) {
			sb.append(block);
			block = block.getPreviousBlock();
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Blockchain)) {
			return false;
		}

		// Since it's a linked list, we solely need to check the last element
		Blockchain blockchain = (Blockchain) o;
		return this.getLastBlock().equals(blockchain.getLastBlock());
	}

}
