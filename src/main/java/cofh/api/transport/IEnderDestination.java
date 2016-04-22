package cofh.api.transport;

public interface IEnderDestination extends IEnderAttuned {

	boolean isNotValid();

	int x();

	int y();

	int z();

	int dimension();

	int getDestination();

	boolean setDestination(int frequency);

	boolean clearDestination();

}
