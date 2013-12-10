/*
 * Copyright 2013 Alex Bogdanovski <albogdano@me.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * You can reach the author at: https://github.com/albogdano
 */
package com.erudika.para.utils.aop;

import com.erudika.para.Para;
import com.erudika.para.persistence.DAO;
import com.erudika.para.core.ParaObject;
import com.erudika.para.core.PObject;
import com.erudika.para.core.Tag;
import com.erudika.para.search.Search;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex Bogdanovski <albogdano@me.com>
 */
public class IndexingAspectIT {
	
	public IndexingAspectIT() {
	}
	
	@BeforeClass
	public static void setUpClass() {
		Para.initialize();
	}
	
	@AfterClass
	public static void tearDownClass() {
		Para.destroy();
	}

	@Test
	public void testInvoke() throws Exception {
		IndexingAspect i = new IndexingAspect();
		
		Tag tag = new Tag("tag");
		Tag tag1 = new Tag("tag1");
		Tag tag2 = new Tag("tag2");
		Tag tag3 = new Tag("tag3");
		
		List<ParaObject> list1 = new ArrayList<ParaObject> ();
		list1.add(tag1);
		list1.add(tag2);
		list1.add(tag3);
		List<PObject> list2 = new ArrayList<PObject> ();
		list2.add(new Tag("tagzz1"));
		list2.add(new Tag("tagzz2"));
		list2.add(new Tag("tagzz3"));
		List<String> badList = new ArrayList<String> ();
		badList.add("XXXtagXXX");
		
		assertSame(tag, IndexingAspect.getArgOfParaObject(new Object[]{tag, "string"}));
		assertNull(IndexingAspect.getArgOfParaObject(new Object[]{"string"}));
		assertEquals(list1, IndexingAspect.getArgOfListOfType(new Object[]{list1}, ParaObject.class));
		assertEquals(list2, IndexingAspect.getArgOfListOfType(new Object[]{list2}, ParaObject.class));
		assertNull(IndexingAspect.getArgOfListOfType(new Object[]{badList}, ParaObject.class));
		
		DAO dao = Para.getDAO();
		Search search = Para.getSearch();
		
		assertNotNull(dao.create(tag));
		assertNotNull(dao.read(tag.getId()));
		assertNotNull(search.findById(tag.getId(), tag.getPlural()));
		
		dao.delete(tag);
		assertNull(dao.read(tag.getId()));
		assertNull(search.findById(tag.getId(), tag.getPlural()));
		
		dao.createAll(list1);
		assertNotNull(dao.read(tag1.getId()));
		assertNotNull(dao.read(tag2.getId()));
		assertNotNull(dao.read(tag3.getId()));
		assertNotNull(search.findById(tag1.getId(), tag.getPlural()));
		assertNotNull(search.findById(tag2.getId(), tag.getPlural()));
		assertNotNull(search.findById(tag3.getId(), tag.getPlural()));
		
		dao.deleteAll(list1);
		assertNull(dao.read(tag1.getId()));
		assertNull(dao.read(tag2.getId()));
		assertNull(dao.read(tag3.getId()));
		assertNull(search.findById(tag1.getId(), tag.getPlural()));
		assertNull(search.findById(tag2.getId(), tag.getPlural()));
		assertNull(search.findById(tag3.getId(), tag.getPlural()));
	}
}