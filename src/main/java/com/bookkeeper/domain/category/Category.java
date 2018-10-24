package com.bookkeeper.domain.category;

import static java.util.Collections.emptyList;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import com.bookkeeper.core.type.TreeNode;
import com.bookkeeper.core.type.CategoryIcon;
import com.bookkeeper.core.type.EntryType;
import com.bookkeeper.core.type.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"parent"})
public class Category extends BaseEntity implements TreeNode<Category> {

  @ManyToOne
  @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_categories_parent_id"))
  protected Category parent;

  @Column(length = 100, nullable = false)
  protected String name;

  @Enumerated(ORDINAL)
  @Column(name = "entry_type")
  protected EntryType entryType;

  @Column(name = "default_amount", scale = 2)
  private BigDecimal defaultAmount;

  @Enumerated(STRING)
  @Column(name = "icon", length = 100)
  private CategoryIcon categoryIcon;

  @Builder
  private static Category buildCategory(Category parent, String name, EntryType entryType,
      BigDecimal defaultAmount, CategoryIcon categoryIcon) {

    return new Category(parent, name, entryType, defaultAmount, categoryIcon);
  }

  @Override
  public List<Category> getChildren() {
    return emptyList();
  }
}