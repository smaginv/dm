package ru.smaginv.debtmanager.dm.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.dm.entity.account.Account;
import ru.smaginv.debtmanager.dm.util.MappingUtil;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountInfoDto;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountUpdateDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = MappingUtil.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AccountMapper {

    @Mapping(target = "person", ignore = true)
    @Mapping(target = "accountType", source = "type")
    @Mapping(target = "currencyCode", source = "currency")
    @Mapping(target = "openDate", source = "openDate", qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(target = "closedDate", source = "closedDate", qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(target = "accountStatus", source = "status")
    Account map(AccountDto accountDto);

    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "type", source = "accountType")
    @Mapping(target = "currency", source = "currencyCode")
    @Mapping(target = "openDate", source = "openDate", qualifiedByName = "formatDateToString")
    @Mapping(target = "closedDate", source = "closedDate", qualifiedByName = "formatDateToString")
    @Mapping(target = "status", source = "accountStatus")
    AccountDto mapDto(Account account);

    @Mapping(target = "operations", ignore = true)
    @Mapping(target = "type", source = "accountType")
    @Mapping(target = "currency", source = "currencyCode")
    @Mapping(target = "openDate", source = "openDate", qualifiedByName = "formatDateToString")
    @Mapping(target = "closedDate", source = "closedDate", qualifiedByName = "formatDateToString")
    @Mapping(target = "status", source = "accountStatus")
    AccountInfoDto mapInfoDto(Account account);

    List<AccountDto> mapDtos(List<Account> accounts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountType", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "currencyCode", ignore = true)
    @Mapping(target = "openDate", ignore = true)
    @Mapping(target = "closedDate", ignore = true)
    @Mapping(target = "accountStatus", ignore = true)
    @Mapping(target = "person", ignore = true)
    void update(AccountUpdateDto accountUpdateDto, @MappingTarget Account account);
}
