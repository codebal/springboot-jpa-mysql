package db.util;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Consumer;

public class TransactionUtils {
	public static void monitor(Consumer<Integer> consumer) {
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			var name = TransactionSynchronizationManager.getCurrentTransactionName();
			var isNew = TransactionAspectSupport.currentTransactionStatus().isNewTransaction();
			System.out.println(
				"=============================================\n"
					+ "트랜잭션 : " + name + "\n"
					+ "isNew : " + isNew + "\n"
					+ "    동기화갯수 : " + TransactionSynchronizationManager.getSynchronizations().size() + "\n"
					+ "    isolationLevel : " +  TransactionSynchronizationManager.getCurrentTransactionIsolationLevel() + "\n"
				//+ "    resourceMap : " +  TransactionSynchronizationManager.getResourceMap()
			);

			TransactionSynchronizationManager.registerSynchronization(
				new TransactionSynchronization() {
					@Override
					public void afterCompletion(int status) {
						TransactionSynchronization.super.afterCompletion(status);
						if (!Objects.isNull(status)) {
							System.out.println(name + " - afterCompletion (status : " + status + ") :: " + LocalDateTime.now());
							consumer.accept(status);
						}
					}

//					@Override
//					public void afterCommit() {
//						TransactionSynchronization.super.afterCommit();
//						System.out.println(name + " - afterCommit :: " + LocalDateTime.now());
//					}
//
//					@Override
//					public void beforeCompletion() {
//						TransactionSynchronization.super.beforeCompletion();
//						System.out.println(name + " - beforeCompletion :: " + LocalDateTime.now());
//					}
//
//					@Override
//					public void beforeCommit(boolean readOnly) {
//						TransactionSynchronization.super.beforeCommit(readOnly);
//						System.out.println(name + "  beforeCommit :: " + LocalDateTime.now());
//					}
				}
			);
		}
	}

}
