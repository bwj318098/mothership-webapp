package com.acss.core.dataentry;

public interface DataEntryService {
	/**
	 * saves the data entry information to HPS
	 * @param dataEntry
	 * @return true if success false if failed.
	 */
	public boolean save(com.acss.core.model.dataentry.DataEntryDTO dataEntry);
}
