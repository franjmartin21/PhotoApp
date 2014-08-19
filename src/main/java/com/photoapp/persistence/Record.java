package com.photoapp.persistence;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author  ubuntu
 */
@MappedSuperclass
public abstract class Record implements Serializable {

	protected long id;

	protected Date createDate;

	protected Date updateDate;

	/**
	 * @return
	 * @uml.property  name="id"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	/**
	 * @param id
	 * @uml.property  name="id"
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return
	 * @uml.property  name="createDate"
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * @return
	 * @uml.property  name="updateDate"
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	@SuppressWarnings("unused")
	@PrePersist
	private void onPersist() {
		if (createDate != null)
			updateDate = new Date();
		else
			updateDate = createDate = new Date(); 
	}
	
	@SuppressWarnings("unused")
	@PreUpdate
	private void onUpdate() {
		updateDate = new Date();
	}
	
	/**
	 * @param createDate
	 * @uml.property  name="createDate"
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param updateDate
	 * @uml.property  name="updateDate"
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
    protected static String string(List<String> list) {
    	StringBuilder s = new StringBuilder();
    	HashSet<String> strung = new HashSet<String>();
    	for (String l: list) {
    		if (!strung.contains(l)) {
	    		s.append(l);
	    		s.append(",");
	    		strung.add(l);
    		}
    	}
    	return s.toString();
    }

    protected static void unstring(String s, List<String> list) {
    	if (s == null) return;
    	StringTokenizer st = new StringTokenizer(s, ",");
    	while (st.hasMoreTokens()) {
    		String t = st.nextToken();
    		if (t.length() > 0)
    			list.add(t);
    	}
    }
    
    @SuppressWarnings("rawtypes")
	protected static String stringEnum(List<? extends Enum> list) {
    	StringBuilder s = new StringBuilder();
    	for (Enum e: list) {
    		s.append(e.name());
    		s.append(",");
    	}
    	return s.toString();
    }

    @SuppressWarnings("rawtypes")
	protected static String stringEnumUnique(List<? extends Enum> list) {
    	StringBuilder s = new StringBuilder();
    	Set<Enum> set = new HashSet<Enum>(list); 
    	for (Enum e: set) {
    		s.append(e.name());
    		s.append(",");
    	}
    	return s.toString();
    }

    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected static <E extends Enum> void unstringEnum(String s, List<E> list, Class<E> enumClass) {
    	if (s == null) return;
    	StringTokenizer st = new StringTokenizer(s, ",");
    	while (st.hasMoreTokens()) {
    		String t = st.nextToken().trim().toUpperCase();
    		if (t.length() > 0)
    			list.add((E)Enum.valueOf(enumClass, t));
    	}
    }
    
	@Override
	public boolean equals(Object other) {
		if (other == null || (other.getClass() != this.getClass())) return false;
		
		Record otherRec = (Record) other;
		
		return otherRec.getId() == getId();
	}
}