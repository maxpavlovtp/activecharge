import React from "react";
import styles from "./Modal.module.css";

export default function Modal({
  active,
  setActive,
  children,
}: {
  active: any;
  setActive: any;
  children: any;
}) {
  return (
    <div
      className={active ? styles.modalActive : styles.modal}
      onClick={() => setActive(false)}
    >
      <div
        className={active ? styles.modalContentActive : styles.modalContent}
        onClick={(e) => e.stopPropagation()}
      >
        {children}
      </div>
    </div>
  );
}
